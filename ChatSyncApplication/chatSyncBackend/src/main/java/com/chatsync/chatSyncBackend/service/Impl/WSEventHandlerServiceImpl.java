package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.WSUtils.WSNotificationTypes;
import com.chatsync.chatSyncBackend.dto.MessageDto;
import com.chatsync.chatSyncBackend.model.*;
import com.chatsync.chatSyncBackend.model.utils.MessageStatus;
import com.chatsync.chatSyncBackend.model.utils.MessageTypes;
import com.chatsync.chatSyncBackend.service.WSEventHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WSEventHandlerServiceImpl implements WSEventHandlerService {
    private final String LOG_TAG = "WSEventHandlerServiceImpl";
    private final NotificationService notificationService;
    private final MessageServiceImpl messageService;
    private final GroupServiceImpl groupService;

    private final UserServiceImpl userService;

    public WSEventHandlerServiceImpl(NotificationService notificationService, MessageServiceImpl messageService, GroupServiceImpl groupService, UserServiceImpl userService) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public void handleWSMessageEvent(JSONObject message) {
        try {
            log.info(LOG_TAG + " handleWSMessageEvent called with message : " + message);
            WSNotificationTypes notificationType = WSNotificationTypes.valueOf((String) message.get("eventType"));
            log.info(LOG_TAG + " received notificationType : {}", notificationType);
            switch (notificationType) {
                case SENT_ONE_TO_ONE_TEXT_MESSAGE:
                    processOneToOneTextMessageEvent(message);
                case SENT_GROUP_TEXT_MESSAGE:
                    processGroupTextMessageEvent(message);
            }
        } catch (Exception e) {
            log.info(LOG_TAG + " Exception in handleWSMessageEvent : " + e.getMessage());
        }
    }

    private void processGroupTextMessageEvent(JSONObject message) {
        log.info("{} processGroupTextMessageEvent called with message : " + message, LOG_TAG);

        JSONObject eventObj = (JSONObject) message.get("eventObject");
        log.info(LOG_TAG + " eventObj " + eventObj.toString());

        String senderId = (String) eventObj.get("from");
        String receiverGroupId = (String) eventObj.get("to");
        String messageContent = (String) eventObj.get("messageContent");
        String threadId = (String) eventObj.get("threadId");

        // Save message to groupDB
        Messages savedMessage = this.messageService.saveGroupMessage(senderId, receiverGroupId, messageContent, threadId);
        log.info("{} saved group message : " + savedMessage, LOG_TAG);

        // publish message confirmation to sender
        MessageDto senderMessageDto = MessageDto.builder()
                .messageType(MessageTypes.GROUP_TEXT)
                .messageDirection(MessageDirection.OUT)
                .senderId((String) eventObj.get("from"))
                .receiverGroupId((String) eventObj.get("to"))
                .messageContent((String) eventObj.get("messageContent"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .messageId(savedMessage.getMessageId())
                .threadId((String) eventObj.get("threadId"))
                .build();

        JSONObject senderObj = new JSONObject();
        senderObj.put("txnId", message.get("txnId"));
        senderObj.put("eventType", WSNotificationTypes.RECEIVE_GROUP_TEXT_MESSAGE_CONFIRM);
        senderObj.put("eventObject", new JSONObject(senderMessageDto));

        this.notificationService.sendEventToUser(senderId, senderObj.toString());

        // publish message event to group members
        publishGroupMessageEventToGroupMembers((String) message.get("txnId"), savedMessage);
    }

    private void publishGroupMessageEventToGroupMembers(String txnId, Messages savedMessage) {
        log.info("{} publishGroupMessageEventToGroupMembers called with : {}", LOG_TAG, savedMessage);

        List<GroupMembers> groupMembers = this.groupService.getMembersForGroup(savedMessage.getReceiverGroup().getGroupId());
        log.info("{} groupMembers cnt : " + groupMembers.size(), LOG_TAG);
        groupMembers.forEach(member -> {
            if (!member.getUser().getUserId().equals(savedMessage.getSender().getUserId())) {
                String receiverId = member.getUser().getUserId();

                MessageDto receiverMessageDto = MessageDto.builder()
                        .messageType(MessageTypes.GROUP_TEXT)
                        .messageDirection(MessageDirection.IN)
                        .senderId(savedMessage.getSender().getUserId())
                        .receiverGroupId(savedMessage.getReceiverGroup().getGroupId())
                        .messageContent(savedMessage.getMessageContent())
                        .createdAt(savedMessage.getCreatedAt())
                        .updatedAt(savedMessage.getUpdatedAt())
                        .messageId(savedMessage.getMessageId())
                        .threadId(savedMessage.getThread().getThreadId())
                        .build();

                JSONObject receiverObj = new JSONObject();
                receiverObj.put("txnId", txnId);
                receiverObj.put("eventType", WSNotificationTypes.RECEIVE_GROUP_TEXT_MESSAGE);
                receiverObj.put("eventObject", new JSONObject(receiverMessageDto));

                this.notificationService.sendEventToUser(receiverId, receiverObj.toString());
            }
        });
    }

    private void processOneToOneTextMessageEvent(JSONObject message) {
        log.info(LOG_TAG + " processOneToOneTextMessageEvent called with message : " + message);

        // publishing WS events to sender and receiver
        JSONObject eventObj = (JSONObject) message.get("eventObject");
        log.info(LOG_TAG + " eventObj " + eventObj.toString());

        MessageDto senderMessageDto = MessageDto.builder()
                .messageType(MessageTypes.ONE_TO_ONE_TEXT)
                .messageDirection(MessageDirection.OUT)
                .senderId((String) eventObj.get("from"))
                .receiverId((String) eventObj.get("to"))
                .messageContent((String) eventObj.get("messageContent"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .messageId(null)
                .threadId((String) eventObj.get("threadId"))
                .build();

        // saving message to the database
        Messages savedMessage = this.messageService.saveOneToOneMessage(senderMessageDto);
        senderMessageDto.setMessageId(savedMessage.getMessageId());

        MessageDto receiverMessageDto = MessageDto.builder()
                .messageType(MessageTypes.ONE_TO_ONE_TEXT)
                .messageDirection(MessageDirection.IN)
                .senderId((String) eventObj.get("from"))
                .receiverId((String) eventObj.get("to"))
                .messageContent((String) eventObj.get("messageContent"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .messageId(savedMessage.getMessageId())
                .threadId((String) eventObj.get("threadId"))
                .build();

        // sending confirm message to sender user
        JSONObject senderObj = new JSONObject();
        senderObj.put("txnId", message.get("txnId"));
        senderObj.put("eventType", WSNotificationTypes.RECEIVE_ONE_TO_ONE_TEXT_CONFIRM);
        senderObj.put("eventObject", new JSONObject(senderMessageDto));

        String senderId = (String) eventObj.get("from");
        this.notificationService.sendEventToUser(senderId, senderObj.toString());

        // receiver event processing
        JSONObject receiverObj = new JSONObject();
        receiverObj.put("txnId", message.get("txnId"));
        receiverObj.put("eventType", WSNotificationTypes.RECEIVE_ONE_TO_ONE_TEXT_MESSAGE);
        receiverObj.put("eventObject", new JSONObject(receiverMessageDto));

        // sending to receiver user
        String receiverId = (String) eventObj.get("to");
        this.notificationService.sendEventToUser(receiverId, receiverObj.toString());
    }
}
