package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.WSUtils.WSEvent;
import org.json.JSONObject;

public interface WSEventHandlerService {
    void handleWSMessageEvent(JSONObject message);
}
