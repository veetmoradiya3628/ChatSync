package com.chatsyncapp.chatSyncApp.service;

import com.chatsyncapp.chatSyncApp.dto.UserRegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRegistrationDto registrationDto);

    ResponseEntity<?> getAllUsers();
}
