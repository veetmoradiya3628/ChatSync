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

    /*
    API to request activation details on mail
     */
    @PostMapping("/request-activation/{emailId}")
    public ResponseEntity<?> requestActivationController(@PathVariable("emailId") String emailId) {
        logger.info(LOG_TAG + " requestActivationController " + emailId);
        return this.userService.requestActivationDetails(emailId);
    }

    /*
        API to activate user account
     */
    @PostMapping("/activate-account/{emailId}/{activationToken}")
    public ResponseEntity<?> activateAccountController(@PathVariable("emailId") String emailId,
                                                       @PathVariable("activationToken") String activationToken){
        logger.info(LOG_TAG + " activateAccountController " + emailId + " " + activationToken);
        return this.userService.activateUserAccount(emailId, activationToken);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<?> getUserInfoController(@PathVariable("userId") String userId)
    {
        logger.info(LOG_TAG + " getUserInfoController called with userId : " + userId);
        return this.userService.getUserInfoByUserId(userId);
    }

}
