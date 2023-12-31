package com.chatsyncapp.chatSyncApp.controller;

import com.chatsyncapp.chatSyncApp.utils.Greeting;
import com.chatsyncapp.chatSyncApp.utils.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Controller
public class MessageController {
    @MessageMapping("/sendmessage")
    @SendTo("/topic/messages")
    public Greeting sendMessage(HelloMessage message) throws InterruptedException {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName() + " " + LocalDateTime.now()) + "!");
    }
}
