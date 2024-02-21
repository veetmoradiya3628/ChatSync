package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.GroupDto;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    public ResponseEntity<?> createGroupService(GroupDto groupDto);

    public ResponseEntity<?> getGroupInformation(String groupId);
}
