package com.chatsyncapp.chatSyncApp.service;

import com.chatsyncapp.chatSyncApp.dto.UserRegistrationDto;
import com.chatsyncapp.chatSyncApp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
