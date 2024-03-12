package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.WSUtils.WSEvent;
import com.chatsync.chatSyncBackend.WSUtils.WSNotificationTypes;
import com.chatsync.chatSyncBackend.WSUtils.messageevents.TextMessageEvent;
import com.chatsync.chatSyncBackend.service.WSEventHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WSEventHandlerServiceImpl implements WSEventHandlerService {
    private final String LOG_TAG = "WSEventHandlerServiceImpl";
    private final NotificationService notificationService;

    public WSEventHandlerServiceImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void handleWSMessageEvent(WSEvent message) {
        try {
            log.info(LOG_TAG + " handleWSMessageEvent called with message : " + message);
            WSNotificationTypes notificationType = message.getEventType();
            switch (notificationType) {
                case SENT_ONE_TO_ONE_TEXT_MESSAGE:
                    processOneToOneTextMessageEvent(message);
            }
        } catch (Exception e) {
            log.info(LOG_TAG + " Exception in handleWSMessageEvent : " + e.getMessage());
        }
    }

    private void processOneToOneTextMessageEvent(WSEvent message) {
        log.info(LOG_TAG + " processOneToOneTextMessageEvent called with message : " + message);
        // DB logic after words - just want to publish message to particular user over WS

        TextMessageEvent eventObj = message.getEventObject();
        log.info(LOG_TAG + " eventObj : " + eventObj);

        TextMessageEvent event = eventObj;
        // sending to receiver user
        String receiverId = event.getTo();
        this.notificationService.sendEventToUser(receiverId, message);
    }
}
