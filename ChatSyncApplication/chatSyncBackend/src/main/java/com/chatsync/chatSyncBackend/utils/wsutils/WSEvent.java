package com.chatsync.chatSyncBackend.utils.wsutils;

import lombok.Data;
import org.json.JSONObject;

@Data
public class WSEvent {
    public String txnId;
    public WSNotificationTypes eventType;
    public JSONObject eventObject;
}
