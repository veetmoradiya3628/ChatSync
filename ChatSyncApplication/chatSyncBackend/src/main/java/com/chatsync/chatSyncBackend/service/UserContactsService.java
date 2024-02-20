package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.UserContactDto;
import org.springframework.http.ResponseEntity;

public interface UserContactsService {
    public ResponseEntity<?> addUserToContact(UserContactDto userContactReq);

    public ResponseEntity<?> deleteUserFromContact(UserContactDto userContactReq);

    public ResponseEntity<?> getContactsForUser(String userId);

    public ResponseEntity<?> getGlobalContactsForUser(String userId);
}
