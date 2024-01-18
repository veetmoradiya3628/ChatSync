package com.chatsyncapp.chatSyncApp.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("User disconnected: " + accessor.getSessionId());
    }
}
