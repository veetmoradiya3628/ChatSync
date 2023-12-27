package com.chatsyncserver.chatSyncServer.controller;

import com.chatsyncserver.chatSyncServer.model.Greeting;
import com.chatsyncserver.chatSyncServer.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.UUID;

@RestController
public class GreetingController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GreetingController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/test")
    @SendTo("/topic/continue")
    public Greeting greetingContinue() throws Exception {
        return new Greeting(UUID.randomUUID().toString());
    }

    @PostMapping("trigger-uuid")
    public String triggerMessage() {
        // Sending a message to the "/topic/queue" destination
        messagingTemplate.convertAndSend("/topic/continue", UUID.randomUUID().toString());
        return "Message triggered and sent to the queue!";
    }

}