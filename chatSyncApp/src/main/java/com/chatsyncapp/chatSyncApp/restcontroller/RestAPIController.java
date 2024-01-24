package com.chatsyncapp.chatSyncApp.restcontroller;

import com.chatsyncapp.chatSyncApp.dto.UserContactMap;
import com.chatsyncapp.chatSyncApp.service.impl.UserContactServiceImpl;
import com.chatsyncapp.chatSyncApp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
        return userContactService.addUserContactAPI(userContactMap);
    }

    @GetMapping("/get-contacts/{userId}")
    public ResponseEntity<?> getContacts(@PathVariable("userId") String userId){
        logger.info(LOGGER_TAG + " getContacts called with userId : " +userId);
        return userContactService.getUserContactsAPI(userId);
    }

    @GetMapping("/get-global-contacts/{userId}")
    public ResponseEntity<?> getGlobalContactsForUser(@PathVariable("userId") String userId){
        logger.info(LOGGER_TAG + " getContacts called with userId : " +userId);
        return userContactService.getGlobalUsersAPI(userId);
    }

    @GetMapping("/userInfo/{userId}")
    public ResponseEntity<?> getUserInfoController(@PathVariable("userId") String userId){
        logger.info(LOGGER_TAG + " getUserInfoController called with userId : " +userId);
        return userContactService.getUserInfo(userId);
    }

}
