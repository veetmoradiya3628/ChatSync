package com.chatsync.chatSyncBackend.dto;

import com.chatsync.chatSyncBackend.model.utils.GroupMemberRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserGroupDto {
    public String groupId;
    public String groupMemberId;
    public String groupName;
    public String groupProfile;
    public Boolean isDeleted;
    public GroupMemberRole groupMemberRole;
    public Integer memberCnt;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
