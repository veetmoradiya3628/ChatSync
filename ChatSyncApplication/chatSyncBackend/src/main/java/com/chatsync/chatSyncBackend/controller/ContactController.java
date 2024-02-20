package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.dto.UserContactDto;
import com.chatsync.chatSyncBackend.service.UserContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@CrossOrigin("*")
public class ContactController {
    private final String LOG_TAG = "ContactController";
    Logger logger = LoggerFactory.getLogger(getClass());
    private UserContactsService userContactsService;

    public ContactController(UserContactsService userContactsService) {
        this.userContactsService = userContactsService;
    }

    @PostMapping("/add-contact")
    public ResponseEntity<?> addContactController(@RequestBody UserContactDto userContactReq){
        logger.info(LOG_TAG + " addContactController called with : " + userContactReq);
        return this.userContactsService.addUserToContact(userContactReq);
    }

    @PostMapping("/delete-contact")
    public ResponseEntity<?> deleteContactController(@RequestBody UserContactDto userContactReq){
        logger.info(LOG_TAG + " addContactController called with : " + userContactReq);
        return this.userContactsService.deleteUserFromContact(userContactReq);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getAllContactsForUserController(@PathVariable("userId") String userId){
        logger.info(LOG_TAG + " getAllContactsForUserController : " + userId);
        return this.userContactsService.getContactsForUser(userId);
    }

    @GetMapping("/global-contacts/{userId}")
    public ResponseEntity<?> getGlobalContactsForUserController(@PathVariable("userId") String userId){
        logger.info(LOG_TAG + " getGlobalContactsForUserController : " + userId);
        return this.userContactsService.getGlobalContactsForUser(userId);
    }
}
