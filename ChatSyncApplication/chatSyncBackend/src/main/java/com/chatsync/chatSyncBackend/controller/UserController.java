package com.chatsync.chatSyncBackend.controller;


import com.chatsync.chatSyncBackend.dto.UserDto;
import com.chatsync.chatSyncBackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final String LOG_TAG = "UserController";
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    /*
     API to register / create new User
     */
    @PostMapping("/register-user")
    public ResponseEntity<?> createNewUser(@RequestBody UserDto userDto) {
        logger.info(LOG_TAG + " createNewUser : " + userDto.toString());
        return this.userService.createNewUserAccount(userDto);
    }

}
