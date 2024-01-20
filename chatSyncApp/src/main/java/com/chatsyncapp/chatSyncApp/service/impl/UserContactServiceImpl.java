package com.chatsyncapp.chatSyncApp.service.impl;

import com.chatsyncapp.chatSyncApp.dto.UserContactMap;
import com.chatsyncapp.chatSyncApp.model.User;
import com.chatsyncapp.chatSyncApp.model.UserContacts;
import com.chatsyncapp.chatSyncApp.repository.UserContactRepository;
import com.chatsyncapp.chatSyncApp.repository.UserRepository;
import com.chatsyncapp.chatSyncApp.service.UserContactService;
import com.chatsyncapp.chatSyncApp.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContactServiceImpl implements UserContactService {
    private static final String LOGGER_TAG = "UserContactServiceImpl ";
    Logger logger = LoggerFactory.getLogger(UserContactServiceImpl.class);

    @Autowired
    private UserContactRepository userContactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserContacts> getUserContacts(String userId) {
        try{
            User user = this.userRepository.findById(userId).get();
            List<UserContacts> contactUsers = this.userContactRepository.findByUserId(user);
            return contactUsers;
        }catch (Exception e){
            logger.info(LOGGER_TAG + " Exception occurred in getUserContacts : " + e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseEntity<?> addUserContact(UserContactMap userContactMap) {
        try{
            logger.info(LOGGER_TAG + " method addUserContact called");
            // validation of request body
            UserContacts userContacts = new UserContacts(
                    this.userRepository.findById(userContactMap.getUserId()).get(),
                    this.userRepository.findById(userContactMap.getContactId()).get()
            );
            UserContacts savedUserContacts = this.userContactRepository.save(userContacts);
            return ResponseHandler.generateResponse("User contact added successfully",
                    HttpStatus.OK,
                    savedUserContacts);
        }catch (Exception e){
            logger.info(LOGGER_TAG + " Exception occurred in addUserContact : " + e.getMessage());
            return null;
        }
    }
}
