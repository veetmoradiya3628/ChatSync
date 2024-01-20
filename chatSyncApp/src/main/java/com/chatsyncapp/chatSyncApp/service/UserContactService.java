package com.chatsyncapp.chatSyncApp.service;

import com.chatsyncapp.chatSyncApp.dto.UserContactMap;
import com.chatsyncapp.chatSyncApp.model.UserContacts;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserContactService {
    List<UserContacts> getUserContacts(String userId);

    ResponseEntity<?> addUserContact(UserContactMap userContactMap);
}
