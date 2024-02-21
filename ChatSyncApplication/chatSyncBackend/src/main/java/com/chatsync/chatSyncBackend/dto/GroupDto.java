package com.chatsync.chatSyncBackend.dto;

import com.chatsync.chatSyncBackend.model.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GroupDto {
    private String groupId;
    private String groupName;
    private String groupProfileImage;
    private Boolean isDeleted;
    private List<UserDto> members;
    private List<UserDto> admins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
