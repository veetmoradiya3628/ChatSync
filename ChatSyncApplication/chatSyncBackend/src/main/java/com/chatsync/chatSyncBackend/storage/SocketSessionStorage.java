package com.chatsync.chatSyncBackend.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SocketSessionStorage {
    private static final String LOG_TAG = "SocketSessionStorage";
    static Logger logger = LoggerFactory.getLogger(SocketSessionStorage.class);
    private static final Map<String, String> userSessionMap = new HashMap<>();
    public static Set<String> onlineUsers = new HashSet<>();

    public static void storeSessionInfo(String username, String sessionId) {
        logger.info(LOG_TAG + " storeSessionInfo called with username : " + username + ", sessionId : " + sessionId);
        userSessionMap.put(username, sessionId);
        onlineUsers.add(username);
        logger.info(LOG_TAG + " userSessions after adding : " + userSessionMap);
        logger.info(LOG_TAG + " online users : " + onlineUsers);
    }

    public static String getSessionId(String username) {
        return userSessionMap.get(username);
    }

    public static void removeSessionInfo(String sessionId){
        logger.info(LOG_TAG + " removeSessionInfo called with sessionId : " + sessionId);
        String username = "";
        for(Map.Entry<String, String> entry : userSessionMap.entrySet()){
            if (entry.getValue().equals(sessionId)){
                logger.info(LOG_TAG + " username associated with provided sessionId : " + entry.getKey());
                username = entry.getKey();
                break;
            }
        }
        if (!Objects.equals(username, "")){
            userSessionMap.remove(username);
            onlineUsers.remove(username);
        }
        logger.info(LOG_TAG + " userSessions after remove : " + userSessionMap);
        logger.info(LOG_TAG + " online users : " + onlineUsers);
    }

    public boolean isUserOnline(String username){
        return onlineUsers.contains(username);
    }
}
