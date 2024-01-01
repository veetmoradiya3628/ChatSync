package com.chatsyncapp.chatSyncApp.controller;

import com.chatsyncapp.chatSyncApp.dto.Message;
import com.chatsyncapp.chatSyncApp.utils.Greeting;
import com.chatsyncapp.chatSyncApp.utils.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Controller
public class MessageController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendmessage")
    @SendTo("/all/messages")
    public Greeting sendMessage(HelloMessage message) throws InterruptedException {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName() + " " + LocalDateTime.now()) + "!");
    }

    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
