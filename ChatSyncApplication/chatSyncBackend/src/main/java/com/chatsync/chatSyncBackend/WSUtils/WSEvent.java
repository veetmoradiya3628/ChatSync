package com.chatsync.chatSyncBackend.WSUtils;

import com.chatsync.chatSyncBackend.WSUtils.messageevents.TextMessageEvent;
import lombok.Data;

@Data
public class WSEvent {
    public String txnId;
    public WSNotificationTypes eventType;
    public TextMessageEvent eventObject;
}
