package com.chatsyncapp.chatSyncApp.controller;

import com.chatsyncapp.chatSyncApp.dto.UserContactMap;
import com.chatsyncapp.chatSyncApp.service.impl.UserContactServiceImpl;
import com.chatsyncapp.chatSyncApp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RestAPIController {
    private static final String LOGGER_TAG = "RestAPIController ";
    private final Logger logger = LoggerFactory.getLogger(RestAPIController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserContactServiceImpl userContactService;

    @GetMapping("/global-users")
    public ResponseEntity<?> getAllActiveUsers(){
        logger.info(LOGGER_TAG + " getAllActiveUsers called");
        return userService.getAllUsers();
    }

    @PostMapping( "/add-contact")
    public ResponseEntity<?> addContactUser(@RequestBody UserContactMap userContactMap){
        logger.info(LOGGER_TAG + " addContactUser called");
        return userContactService.addUserContact(userContactMap);
    }

}
