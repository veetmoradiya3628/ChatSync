package com.chatsync.chatSyncBackend.listners;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Extract username from request headers
        System.out.println(request.getHeaders());
        String username = request.getHeaders().getFirst("username");
        System.out.println("username at before handshake : " + username);

        // Add the username to attributes for later use in WebSocket session
        attributes.put("username", username);

        // Your custom logic here...

        // Allow the handshake to continue
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Your custom logic after handshake...
    }
}
