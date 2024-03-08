package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.ThreadDto;
import org.springframework.http.ResponseEntity;

public interface ThreadService {
    public ResponseEntity<?> createNewThreadService(ThreadDto threadDto);

    public ResponseEntity<?> getThreadsForUserService(String userId);
}
