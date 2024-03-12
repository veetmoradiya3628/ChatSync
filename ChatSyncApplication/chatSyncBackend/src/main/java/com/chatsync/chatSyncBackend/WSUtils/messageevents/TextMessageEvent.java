package com.chatsync.chatSyncBackend.WSUtils.messageevents;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TextMessageEvent {
    public String threadId;
    public String from;
    public String to;
    public String messageContent;
    public LocalDateTime sentAt;
    public Boolean drReq;
}
