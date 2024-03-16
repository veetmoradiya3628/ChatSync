package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.WSUtils.WSNotificationTypes;
import com.chatsync.chatSyncBackend.dto.MessageDto;
import com.chatsync.chatSyncBackend.model.MessageDirection;
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

    public WSEventHandlerServiceImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
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
        // DB logic after words - just want to publish message to particular user over WS
        JSONObject eventObj = (JSONObject) message.get("eventObject");

        MessageDto messageDto = MessageDto.builder()
                .messageId(String.valueOf(UUID.randomUUID()))
                .messageStatus(MessageStatus.SENT)
                .messageDirection(MessageDirection.OUT)
                .messageType(MessageTypes.ONE_TO_ONE_TEXT)
                .senderId((String) eventObj.get("from"))
                .receiverId((String) eventObj.get("to"))
                .messageContent((String) eventObj.get("messageContent"))
                .threadId((String) eventObj.get("threadId"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        // sending to receiver user
        String receiverId = (String) eventObj.get("to");
        this.notificationService.sendEventToUser(receiverId, message.toString());

        // sending dto to sender user
        String senderId = (String) eventObj.get("from");
        this.notificationService.sendEventToUser(senderId, messageDto);
    }
}
