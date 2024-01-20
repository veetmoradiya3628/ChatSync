package com.chatsyncapp.chatSyncApp.service;

import com.chatsyncapp.chatSyncApp.dto.UserDTO;

import java.util.List;

public interface ContactsService {
    List<UserDTO> getAllActiveUserExpectRequestedUser(String username);

    List<UserDTO> getContactUsers(String requestedUser);
}
