package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.MessageDto;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    public ResponseEntity<?> sentMessageService(MessageDto messageDto);

    public ResponseEntity<?> getMessagesForThreadService(String threadId, int page, int size, String userId);
}
