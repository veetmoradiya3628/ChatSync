package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.GroupDto;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    public ResponseEntity<?> createGroupService(GroupDto groupDto);

    public ResponseEntity<?> getGroupInformation(String groupId);

    public ResponseEntity<?> addMemberToGroup(String groupId, String userId, String groupMemberRole);

    public ResponseEntity<?> removeMemberFromGroup(String groupId, String userId);

    public ResponseEntity<?> updateUserRoleToGroup(String userId, String role, String groupId);

    public ResponseEntity<?> deleteGroup(String groupId);

    public ResponseEntity<?> getGroupsForUser(String userId);
}
