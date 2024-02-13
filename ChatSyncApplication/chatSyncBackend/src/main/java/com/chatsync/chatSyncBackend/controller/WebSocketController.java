package com.chatsync.chatSyncBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/reply")
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
}
