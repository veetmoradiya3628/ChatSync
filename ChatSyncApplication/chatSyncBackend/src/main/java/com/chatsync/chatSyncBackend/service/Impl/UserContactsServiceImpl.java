package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.dto.UserContactDto;
import com.chatsync.chatSyncBackend.dto.UserDto;
import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.model.UserContacts;
import com.chatsync.chatSyncBackend.repostiroy.UserContactsRepository;
import com.chatsync.chatSyncBackend.repostiroy.UserRepository;
import com.chatsync.chatSyncBackend.service.UserContactsService;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserContactsServiceImpl implements UserContactsService {
    private static String LOG_TAG = "UserContactsServiceImpl";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserContactsRepository userContactsRepository;
    private UserRepository userRepository;
    private UserServiceImpl userServiceImpl;

    public UserContactsServiceImpl(UserContactsRepository userContactsRepository, UserRepository userRepository, UserServiceImpl userServiceImpl) {
        this.userContactsRepository = userContactsRepository;
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public ResponseEntity<?> addUserToContact(UserContactDto userContactData) {
        try {
            logger.info(LOG_TAG + " addUserToContact called with " + userContactData);

            String userId = userContactData.getUserId();
            String contactId = userContactData.getContactId();

            if (this.userServiceImpl.isUserExistsById(userId) &&
                    this.userServiceImpl.isUserExistsById(contactId)) {

                if (!this.userContactsRepository.existsByUserAndContact(new User(userId), new User(contactId))) {

                    UserContacts userContact = UserContacts.builder()
                            .contact(this.userRepository.findById(contactId).get())
                            .user(this.userRepository.findById(userId).get()).build();

                    logger.info(LOG_TAG + " userContact format for request : " + userContact.toString());

                    UserContacts savedUserContact = this.userContactsRepository.save(userContact);
                    return ResponseHandler.generateResponse("user added as contact successfully!!", HttpStatus.OK, savedUserContact);

                } else {
                    return ResponseHandler.generateResponse("user is already part of contact member list!!", HttpStatus.OK, null);
                }
            } else {
                return ResponseHandler.generateResponse("Invalid request data!!", HttpStatus.NOT_ACCEPTABLE, null);
            }
        } catch (Exception e) {
            logger.info(LOG_TAG + " addToContact Exception : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured while adding to contact", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> deleteUserFromContact(UserContactDto userContactReq) {
        try {
            logger.info(LOG_TAG + " deleteUserFromContact called with " + userContactReq);

            String userId = userContactReq.getUserId();
            String contactId = userContactReq.getContactId();

            if (this.userServiceImpl.isUserExistsById(userId) &&
                    this.userServiceImpl.isUserExistsById(contactId)) {

                if (this.userContactsRepository.existsByUserAndContact(new User(userId), new User(contactId))) {

                    UserContacts userContact = this.userContactsRepository.findByUserAndContact(new User(userId), new User(contactId));
                    logger.info(LOG_TAG + " userContact found : " + userContact.toString());
                    this.userContactsRepository.deleteById(userContact.getUserContactId());
                    return ResponseHandler.generateResponse("user deleted from contact list successfully!!", HttpStatus.OK, null);

                } else {
                    return ResponseHandler.generateResponse("user is not part of contact member list!!", HttpStatus.OK, null);
                }
            } else {
                return ResponseHandler.generateResponse("Invalid request data!!", HttpStatus.NOT_ACCEPTABLE, null);
            }

        } catch (Exception e) {
            logger.info(LOG_TAG + " deleteUserFromContact Exception : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured while deleting the contact", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> getContactsForUser(String userId) {
        try {
            logger.info(LOG_TAG + " getContactsForUser called with userId : " + userId);
            if (this.userServiceImpl.isUserExistsById(userId)) {
                List<UserContacts> contacts = this.userContactsRepository.findByUser(new User(userId));
                List<UserContactDto> userContactsResp = new ArrayList<>();
                contacts.forEach(contact -> {
                    UserDto contactDetail = UserDto.builder()
                            .userId(contact.getContact().getUserId())
                            .username(contact.getContact().getUsername())
                            .email(contact.getContact().getEmail())
                            .profileImage(contact.getContact().getProfileImage())
                            .firstName(contact.getContact().getFirstName())
                            .lastName(contact.getContact().getLastName())
                            .isActive(contact.getContact().isActive())
                            .phoneNo(contact.getContact().getPhoneNo())
                            .createdAt(contact.getContact().getCreatedAt())
                            .updatedAt(contact.getContact().getUpdatedAt())
                            .build();

                    UserContactDto userContact = UserContactDto.builder()
                            .userContactId(contact.getUserContactId())
                            .contactDetail(contactDetail)
                            .build();

                    userContactsResp.add(userContact);
                });
                logger.info(LOG_TAG + " final user contact list : " + userContactsResp);
                return ResponseHandler.generateResponse("user contacts list successfully retrieved!!", HttpStatus.OK, userContactsResp);
            } else {
                logger.info(LOG_TAG + " user not exists with provided userId : " + userId);
                return ResponseHandler.generateResponse("user not exists with userId : " + userId, HttpStatus.OK, null);
            }
        } catch (Exception e) {
            logger.info(LOG_TAG + " getContactsForUser Exception : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured while getting the contacts for user", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> getGlobalContactsForUser(String userId) {
        try {
            logger.info(LOG_TAG + " getGlobalContactsForUser called with userId : " + userId);
            if (this.userServiceImpl.isUserExistsById(userId)) {
                List<User> allUsers = this.userRepository.findAll();
                List<UserContacts> userContacts = this.userContactsRepository.findByUser(new User(userId));
                List<User> contacts = new ArrayList<>();
                userContacts.forEach(userContact -> {
                    contacts.add(userContact.getContact());
                });

                logger.info(LOG_TAG + " allUsers cnt : " + allUsers.size());
                logger.info(LOG_TAG + " contacts cnt : " + contacts.size());
                allUsers.removeAll(contacts);
                logger.info(LOG_TAG + " allUsers cnt after removing contacts : " + allUsers.size());
                List<UserDto> respUsers = new ArrayList<>();
                allUsers.forEach(user -> {
                    // handling skipping self
                    if(!user.getUserId().equals(userId)){
                        UserDto userDto = UserDto.builder()
                                .userId(user.getUserId())
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .profileImage(user.getProfileImage())
                                .username(user.getUsername())
                                .createdAt(user.getCreatedAt())
                                .updatedAt(user.getUpdatedAt())
                                .isActive(user.isActive())
                                .phoneNo(user.getPhoneNo())
                                .build();
                        respUsers.add(userDto);
                    }
                });
                return ResponseHandler.generateResponse("user global contacts list successfully retrieved!!", HttpStatus.OK, respUsers);
            } else {
                logger.info(LOG_TAG + " user not exists with provided userId : " + userId);
                return ResponseHandler.generateResponse("user not exists with userId : " + userId, HttpStatus.OK, null);
            }
        } catch (Exception e) {
            logger.info(LOG_TAG + " getContactsForUser Exception : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured while getting the contacts for user", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
