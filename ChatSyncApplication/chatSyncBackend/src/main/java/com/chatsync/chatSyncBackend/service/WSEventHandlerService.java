package com.chatsync.chatSyncBackend.service;

import org.json.JSONObject;

public interface WSEventHandlerService {
    void handleWSMessageEvent(JSONObject message);
}
