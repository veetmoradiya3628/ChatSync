package com.chatsync.chatSyncBackend.dto;

import com.chatsync.chatSyncBackend.model.utils.ConversationType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ThreadDto {
    private String threadId;
    private ConversationType conversationType;
    private String conversationId;
    private String conversationGroupId;
    private String conversationName;
    private MessageDto lastMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
