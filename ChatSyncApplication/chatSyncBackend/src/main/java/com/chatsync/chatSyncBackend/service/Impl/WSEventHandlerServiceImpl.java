package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.WSUtils.WSNotificationTypes;
import com.chatsync.chatSyncBackend.dto.MessageDto;
import com.chatsync.chatSyncBackend.model.MessageDirection;
import com.chatsync.chatSyncBackend.model.Messages;
import com.chatsync.chatSyncBackend.model.utils.MessageStatus;
import com.chatsync.chatSyncBackend.model.utils.MessageTypes;
import com.chatsync.chatSyncBackend.service.WSEventHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class WSEventHandlerServiceImpl implements WSEventHandlerService {
    private final String LOG_TAG = "WSEventHandlerServiceImpl";
    private final NotificationService notificationService;
    private final MessageServiceImpl messageService;

    public WSEventHandlerServiceImpl(NotificationService notificationService, MessageServiceImpl messageService) {
        this.notificationService = notificationService;
        this.messageService = messageService;
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
            }
        } catch (Exception e) {
            log.info(LOG_TAG + " Exception in handleWSMessageEvent : " + e.getMessage());
        }
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
