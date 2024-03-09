package com.chatsync.chatSyncBackend.dto;

import com.chatsync.chatSyncBackend.model.MessageDirection;
import com.chatsync.chatSyncBackend.model.utils.MessageStatus;
import com.chatsync.chatSyncBackend.model.utils.MessageTypes;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageDto {
    public String messageId;
    public MessageTypes messageType;
    public String senderId;
    public String messageContent;
    public String messageRefUrl;
    public String threadId;
    public String receiverId;
    public String receiverGroupId;
    public MessageDirection messageDirection;
    public MessageStatus messageStatus;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
