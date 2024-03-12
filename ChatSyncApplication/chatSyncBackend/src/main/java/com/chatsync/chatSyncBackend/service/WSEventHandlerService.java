package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.WSUtils.WSEvent;

public interface WSEventHandlerService {
    void handleWSMessageEvent(WSEvent message);
}
