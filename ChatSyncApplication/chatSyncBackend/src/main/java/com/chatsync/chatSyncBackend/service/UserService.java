package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.dto.UserDto;
import com.chatsync.chatSyncBackend.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User getUserByUsername(String username);

    ResponseEntity<?> createNewUserAccount(UserDto userDto);
}
