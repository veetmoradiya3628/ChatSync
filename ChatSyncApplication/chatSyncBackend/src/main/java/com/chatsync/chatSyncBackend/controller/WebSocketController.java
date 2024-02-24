package com.chatsync.chatSyncBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class WebSocketController {

    private final SimpUserRegistry userRegistry;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpUserRegistry userRegistry, SimpMessagingTemplate messagingTemplate) {
        this.userRegistry = userRegistry;
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/message")
    @SendTo("/topic/global")
    public String greeting(String message) throws Exception {
        System.out.println("message : " + message);
        return message + " " + LocalDateTime.now();
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publishMessageToQueueController(@RequestParam String queue,
                                                             @RequestParam String message){
        message = message + LocalDateTime.now().toString();
        this.messagingTemplate.convertAndSend("/topic/" + queue, message);
        return ResponseEntity.ok(null);
    }

    /*
     Not working somehow need to check
     */
    @GetMapping("/connected-users")
    public ResponseEntity<?> connectedUsers(){
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
                                                                   @RequestParam String message){

        message = message + LocalDateTime.now().toString();
        this.messagingTemplate.convertAndSend("/topic/private/" + username, message);
        return ResponseEntity.ok(null);
    }
}
