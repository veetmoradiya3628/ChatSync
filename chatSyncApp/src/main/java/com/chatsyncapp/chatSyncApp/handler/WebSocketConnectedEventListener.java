package com.chatsyncapp.chatSyncApp.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        System.out.println(event.toString());
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("User connected: " + accessor.getSessionId());
    }
}
