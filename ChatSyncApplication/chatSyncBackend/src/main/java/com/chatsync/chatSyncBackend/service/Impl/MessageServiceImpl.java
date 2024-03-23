package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.dto.MessageDto;
import com.chatsync.chatSyncBackend.model.*;
import com.chatsync.chatSyncBackend.model.utils.ConversationType;
import com.chatsync.chatSyncBackend.model.utils.MessageStatus;
import com.chatsync.chatSyncBackend.model.utils.MessageTypes;
import com.chatsync.chatSyncBackend.repostiroy.MessagesRepository;
import com.chatsync.chatSyncBackend.service.MessageService;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@Transactional
public class MessageServiceImpl implements MessageService {
    private final String LOG_TAG = "MessageServiceImpl";
    private UserServiceImpl userService;
    private ThreadServiceImpl threadService;
    private MessagesRepository messagesRepository;
    private GroupServiceImpl groupService;

    public MessageServiceImpl(UserServiceImpl userService, ThreadServiceImpl threadService, MessagesRepository messagesRepository, GroupServiceImpl groupService) {
        this.userService = userService;
        this.threadService = threadService;
        this.messagesRepository = messagesRepository;
        this.groupService = groupService;
    }

    @Override
    public ResponseEntity<?> sentMessageService(MessageDto messageDto) {
        try {
            log.info(LOG_TAG + " sentMessageService called with " + messageDto);
            MessageTypes messageType = messageDto.getMessageType();
            switch (messageType) {
                case ONE_TO_ONE_TEXT:
                    return handleOneToOneTextMessageRequest(messageDto);
                case GROUP_TEXT:
                    return handleGroupTextMessageRequest(messageDto);
            }
            return ResponseHandler.generateResponse("Messaging not supported!!", HttpStatus.OK, null);
        } catch (Exception e) {
            log.info(LOG_TAG + " exception in sentMessageService " + e.getMessage());
            return ResponseHandler.generateResponse("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> getMessagesForThreadService(String threadId, int page, int size, String userId) {
        try {
            log.info("{} getMessagesForThreadService called with threadId: {}, page: {} and size : {} by userId : {}", LOG_TAG, threadId, page, size, userId);
            if (this.threadService.isThreadExistsById(threadId)) {

                ConversationType conversationType = this.threadService.getConversationTypeForThread(threadId);

                if (conversationType == ConversationType.ONE_TO_ONE) {
                    Pageable pageable = PageRequest.of(page, size);
                    Page<Messages> messages = this.messagesRepository.findByThreadOrderByCreatedAtDesc(new Threads(threadId), pageable);
                    Page<MessageDto> resp = messages.map((message) -> MessageDto.builder()
                            .messageId(message.getMessageId())
                            .messageType(message.getMessageType())
                            .messageContent(message.getMessageContent())
                            .messageDirection(getMessageDirection(message, userId))
                            .messageStatus(message.getMessageStatus())
                            .messageRefUrl(message.getMessageRefUrl())
                            .receiverId(message.getReceiver().getUserId())
                            .senderId(message.getSender().getUserId())
                            .threadId(message.getThread().getThreadId())
                            .createdAt(message.getCreatedAt())
                            .updatedAt(message.getUpdatedAt())
                            .build());
                    return ResponseHandler.generateResponse("Ok", HttpStatus.OK, resp);
                } else {
                    Pageable pageable = PageRequest.of(page, size);
                    Page<Messages> messages = this.messagesRepository.findByThreadOrderByCreatedAtDesc(new Threads(threadId), pageable);
                    Page<MessageDto> resp = messages.map((message) -> MessageDto.builder()
                            .messageId(message.getMessageId())
                            .messageType(message.getMessageType())
                            .messageContent(message.getMessageContent())
                            .messageDirection(getMessageDirection(message, userId))
                            .messageStatus(message.getMessageStatus())
                            .messageRefUrl(message.getMessageRefUrl())
                            .receiverGroupId(message.getReceiverGroup().getGroupId()) // groupId was giving error
                            .senderId(message.getSender().getUserId())
                            .messageSenderName(this.userService.getUserFullNameByUserId(message.getSender().getUserId()))
                            .threadId(message.getThread().getThreadId())
                            .createdAt(message.getCreatedAt())
                            .updatedAt(message.getUpdatedAt())
                            .build());
                    return ResponseHandler.generateResponse("Ok", HttpStatus.OK, resp);
                }
            } else {
                log.info("{} thread not exists with threadId : {}", LOG_TAG, threadId);
                return ResponseHandler.generateResponse("Thread not exists with provided threadId", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            log.info(LOG_TAG + " exception in getMessagesForThreadService " + e.getMessage());
            return ResponseHandler.generateResponse("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private ResponseEntity<?> handleOneToOneTextMessageRequest(MessageDto messageDto) {
        String senderId = messageDto.getSenderId();
        String receiverId = messageDto.getReceiverId();
        String threadId = messageDto.getThreadId();
        if (this.userService.isUserExistsById(senderId) && this.userService.isUserExistsById(receiverId) && this.threadService.isThreadExistsById(threadId)) {

            // convId validation and thread, if thread not exists in that need to add new thread entry and then will have to proceed

            Messages message = Messages.builder()
                    .messageType(messageDto.getMessageType())
                    .messageContent(messageDto.getMessageContent())
                    .isDeleted(false)
                    .sender(new User(senderId))
                    .receiver(new User(receiverId))
                    .thread(new Threads(threadId))
                    .messageStatus(MessageStatus.SENT)
                    .build();

            log.info(LOG_TAG + " created Message Object to be stored : " + message);
            message = this.messagesRepository.save(message);
            log.info(LOG_TAG + " saved Message Object : " + message);
            return ResponseHandler.generateResponse("Ok", HttpStatus.OK, message);
        } else {
            log.info(LOG_TAG + " Sender, receiver or thread is not valid");
            return ResponseHandler.generateResponse("Sender, receiver or thread is not valid", HttpStatus.NOT_ACCEPTABLE, null);
        }
    }

    private ResponseEntity<?> handleGroupTextMessageRequest(MessageDto messageDto) {
        return ResponseHandler.generateResponse("Not Implemented Yet", HttpStatus.OK, null);
    }

    private MessageDirection getMessageDirection(Messages message, String userId) {
        if (message.getSender().getUserId().equals(userId)) {
            return MessageDirection.OUT;
        } else {
            return MessageDirection.IN;
        }
    }

    public Messages saveOneToOneMessage(MessageDto messageDto) {
        String senderId = messageDto.getSenderId();
        String receiverId = messageDto.getReceiverId();
        String threadId = messageDto.getThreadId();
        if (this.userService.isUserExistsById(senderId) && this.userService.isUserExistsById(receiverId) && this.threadService.isThreadExistsById(threadId)) {

            // convId validation and thread, if thread not exists in that need to add new thread entry and then will have to proceed

            Messages message = Messages.builder()
                    .messageType(messageDto.getMessageType())
                    .messageContent(messageDto.getMessageContent())
                    .isDeleted(false)
                    .sender(new User(senderId))
                    .receiver(new User(receiverId))
                    .thread(new Threads(threadId))
                    .messageStatus(MessageStatus.SENT)
                    .build();

            log.info(LOG_TAG + " created Message Object to be stored : " + message);
            return this.messagesRepository.save(message);
        } else {
            return null;
        }
    }

    public Messages saveGroupMessage(String senderId, String receiverGroupId, String messageContent, String threadId) {
        log.info("senderId : {}, receiverGroupId : {}, messageContent : {}, threadId : {}", senderId, receiverGroupId, messageContent, threadId);
        log.info("{} saveGroupMessage called...", LOG_TAG);
        if (this.threadService.isThreadExistsById(threadId) && this.userService.isUserExistsById(senderId) && this.groupService.isGroupExistsById(receiverGroupId)) {
            Messages message = Messages.builder()
                    .messageType(MessageTypes.GROUP_TEXT)
                    .messageContent(messageContent)
                    .isDeleted(false)
                    .sender(new User(senderId))
                    .receiverGroup(new Group(receiverGroupId))
                    .thread(new Threads(threadId))
                    .messageStatus(MessageStatus.SENT)
                    .build();

            log.info(LOG_TAG + " created group Message Object to be stored : " + message);
            return this.messagesRepository.save(message);
        } else {
            log.info("{} something wrong is request parameter...", LOG_TAG);
            return null;
        }
    }
}
