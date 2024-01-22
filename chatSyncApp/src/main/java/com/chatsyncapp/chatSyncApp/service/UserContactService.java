package com.chatsyncapp.chatSyncApp.service;

import com.chatsyncapp.chatSyncApp.dto.UserContactMap;
import com.chatsyncapp.chatSyncApp.dto.UserContactsDTO;
import com.chatsyncapp.chatSyncApp.model.UserContacts;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserContactService {
    UserContactsDTO getUserContacts(String userId);

    ResponseEntity<?> addUserContactAPI(UserContactMap userContactMap);

    ResponseEntity<?> getUserContactsAPI(String userId);

    ResponseEntity<?> getGlobalUsersAPI(String userId);
}
