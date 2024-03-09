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
    private String userA;
    private String userB;
    private ConversationType conversationType;
    private String conversationId;
    private String conversationGroupId;
    private String conversationName;
    private String profileImage;
    private MessageDto lastMessage;
    private Integer memberCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
