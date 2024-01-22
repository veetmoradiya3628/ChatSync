package com.chatsyncapp.chatSyncApp.service.impl;

import com.chatsyncapp.chatSyncApp.dto.UserDTO;
import com.chatsyncapp.chatSyncApp.model.User;
import com.chatsyncapp.chatSyncApp.repository.UserRepository;
import com.chatsyncapp.chatSyncApp.service.ContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContactsServiceImpl implements ContactsService {
    private static final String LOGGER_TAG = "ContactsServiceImpl : ";
    Logger logger = LoggerFactory.getLogger(ContactsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserContactServiceImpl userContactService;
    @Override
    public List<UserDTO> getAllActiveUserExpectRequestedUser(String username) {
        try {
            logger.info(LOGGER_TAG + " getAllActiveUserExpectRequestedUser called with username : " + username);
            List<User> listOfUsers = this.userRepository.findAll();
            logger.info(listOfUsers.toString());
            List<User> filteredListOfUsers = listOfUsers.stream().filter(u -> !Objects.equals(u.getEmail(), username)).toList();
            logger.info(filteredListOfUsers.toString());
            List<UserDTO> respUsers = new ArrayList<>();
            filteredListOfUsers.forEach(user -> {
                UserDTO u = UserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .profileImage(user.getProfileImage())
                        .build();
                respUsers.add(u);
            });
            return respUsers;
        }catch (Exception e){
            logger.info(LOGGER_TAG + " Exception occurred in getAllActiveUserExpectRequestedUser : " + e.getMessage());
            return null;
        }
    }
}
