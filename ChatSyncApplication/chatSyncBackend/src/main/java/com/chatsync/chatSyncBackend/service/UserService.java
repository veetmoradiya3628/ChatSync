package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.UserDto;
import com.chatsync.chatSyncBackend.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User getUserByUsername(String username);

    ResponseEntity<?> createNewUserAccount(UserDto userDto);

    ResponseEntity<?> requestActivationDetails(String emailId);

    ResponseEntity<?> activateUserAccount(String emailId, String activationToken);

    ResponseEntity<?> getUserInfoByUserId(String userId);
}
