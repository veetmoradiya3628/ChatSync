package com.chatsync.chatSyncBackend.listners;

import com.chatsync.chatSyncBackend.storage.SocketSessionStorage;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Component
public class WebSocketConnectEventListener implements ApplicationListener<SessionConnectedEvent> {
    private final Logger logger = LoggerFactory.getLogger(WebSocketConnectEventListener.class);
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        String LOG_TAG = "WebSocketConnectEventListener";
        logger.info(LOG_TAG + " onApplicationEvent connect called with : " + event);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        logger.info(LOG_TAG + " accessor : " + accessor);
        // Access connection details
        String sessionId = accessor.getSessionId();
        String username = getUserId(accessor);

        logger.info(LOG_TAG + " sessionId : " + sessionId);
        logger.info(LOG_TAG + " username : " + username);

        logger.info("WebSocket connection opened. SessionId: " + sessionId + ", Username: " + username);

        // Your custom logic here...
        SocketSessionStorage.storeSessionInfo(username, sessionId);
    }

    private String getUserId(StompHeaderAccessor accessor) {
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if (nonNull(generic)) {
            SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
            List<String> username = nativeAccessor.getNativeHeader("username");
            return isNull(username) ? null : username.stream().findFirst().orElse(null);
        }
        return null;
    }
}
