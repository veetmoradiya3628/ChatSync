package com.chatsyncapp.chatSyncApp.controller;

import com.chatsyncapp.chatSyncApp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class RestController {
    private static final String LOGGER_TAG = "RestController : ";
    private Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/global-users")
    public ResponseEntity<?> getAllActiveUsers(){
        logger.info(LOGGER_TAG + " getAllActiveUsers called");
        return userService.getAllUsers();
    }

}
