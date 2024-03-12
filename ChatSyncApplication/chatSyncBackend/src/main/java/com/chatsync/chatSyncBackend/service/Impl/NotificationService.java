package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.storage.SocketSessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    private static String LOG_TAG = "NotificationService";
    private final SimpMessagingTemplate messagingTemplate;
    private final SocketSessionStorage storage;
    private final UserServiceImpl userService;

    public NotificationService(SimpMessagingTemplate messagingTemplate, SocketSessionStorage storage, UserServiceImpl userService) {
        this.messagingTemplate = messagingTemplate;
        this.storage = storage;
        this.userService = userService;
    }

    public void sendEventToUser(String receiverId, Object payload) {
        log.info(LOG_TAG + " sendEventToUser called for receiverId : " + receiverId);
        String username = this.userService.getUserEmailByUserId(receiverId);
        if(this.storage.isUserOnline(username)){
            log.info(LOG_TAG + " {} is online so going for event publish", receiverId);
            this.messagingTemplate.convertAndSend("/topic/private/" + username, payload);
        }else{
            log.info(LOG_TAG + " {} is offline so not going for event publish", receiverId);
            // have to add additional logic to store notification table so once user online then he will get it.
        }
    }
}
