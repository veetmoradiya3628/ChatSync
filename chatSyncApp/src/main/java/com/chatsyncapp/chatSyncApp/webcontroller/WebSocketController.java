package com.chatsyncapp.chatSyncApp.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebSocketController {
    @Autowired
    private SimpUserRegistry userRegistry;

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @GetMapping("/checkSession/{username}")
    @ResponseBody
    public String checkWebSocketSession(@PathVariable String username){
        if (isSessionActive(username)){
            return "Websocket session is active for user : " + username;
        }else{
            return "Websocket session is not active for user : "+ username;
        }
    }

    private boolean isSessionActive(String username){
        return userRegistry.getUser(username) != null;
    }
}
