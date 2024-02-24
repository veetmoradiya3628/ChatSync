package com.chatsync.chatSyncBackend.listners;

import com.chatsync.chatSyncBackend.storage.SocketSessionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    private final Logger logger = LoggerFactory.getLogger(WebSocketDisconnectEventListener.class);

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String LOG_TAG = "WebSocketDisconnectEventListener";
        logger.info(LOG_TAG + " onApplicationEvent disconnect called with : " + event);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = accessor.getSessionId();

        // Execute code on connect (close)
        SocketSessionStorage.removeSessionInfo(sessionId);

        // Your custom logic here...
        logger.info("WebSocket connection closed. SessionId: " + sessionId);
    }

}
