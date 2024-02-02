package com.chatsync.chatSyncBackend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class WebSocketController {

    @MessageMapping("/message")
    @SendTo("/topic/reply")
    public String greeting(String message) throws Exception {
        System.out.println("message : " + message);
        return message + " " + LocalDateTime.now();
    }
}
