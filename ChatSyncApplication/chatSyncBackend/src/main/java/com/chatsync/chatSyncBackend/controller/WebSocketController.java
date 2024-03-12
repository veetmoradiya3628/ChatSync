package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.WSUtils.WSEvent;
import com.chatsync.chatSyncBackend.dto.MessageDto;
import com.chatsync.chatSyncBackend.service.WSEventHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@CrossOrigin("*")
@Slf4j
public class WebSocketController {

    private final SimpUserRegistry userRegistry;
    private final SimpMessagingTemplate messagingTemplate;
    private final WSEventHandlerService wsEventHandlerService;

    public WebSocketController(SimpUserRegistry userRegistry, SimpMessagingTemplate messagingTemplate, WSEventHandlerService wsEventHandlerService) {
        this.userRegistry = userRegistry;
        this.messagingTemplate = messagingTemplate;
        this.wsEventHandlerService = wsEventHandlerService;
    }


    @MessageMapping("/message")
    public void sentMessageWSHandler(WSEvent message) throws Exception {
        log.info("sentMessageWSHandler received message : {}", message);
        this.wsEventHandlerService.handleWSMessageEvent(message);
    }

    /*
        Controller to publish message to global users
    */
    @PostMapping("/global-publish")
    public ResponseEntity<?> publishMessageToQueueController(@RequestParam String messagePrefix) {

        String message = messagePrefix + LocalDateTime.now();

        this.messagingTemplate.convertAndSend("/topic/global", message);
        return ResponseEntity.ok("message for global queue published successfully");
    }

    /*
     Not working somehow need to check
     */
    @GetMapping("/connected-users")
    public ResponseEntity<?> connectedUsers() {
        StringBuilder result = new StringBuilder("Connected Users:\n");
        System.out.println(userRegistry.getUsers() + " " + userRegistry.getUserCount());

        // Retrieve the list of connected users
        for (SimpUser user : userRegistry.getUsers()) {
            System.out.println(user);
            result.append("Username: ").append(user.getName()).append(", Sessions: ").append(user.getSessions()).append("\n");
        }

        return ResponseEntity.ok(result.toString());
    }

    @PostMapping("/publish-private-message")
    public ResponseEntity<?> publishPrivateMessageToUserController(@RequestParam String username,
                                                                   @RequestParam String message) {

        message = message + LocalDateTime.now().toString();
        this.messagingTemplate.convertAndSend("/topic/private/" + username, message);
        return ResponseEntity.ok(null);
    }
}
