package com.chatsyncapp.chatSyncApp.service.impl;

import com.chatsyncapp.chatSyncApp.dto.UserContactMap;
import com.chatsyncapp.chatSyncApp.dto.UserContactsDTO;
import com.chatsyncapp.chatSyncApp.dto.UserDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserContactServiceImpl implements UserContactService {
    private static final String LOGGER_TAG = "UserContactServiceImpl ";
    Logger logger = LoggerFactory.getLogger(UserContactServiceImpl.class);

    @Autowired
    private UserContactRepository userContactRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserContactsDTO getUserContacts(String userId) {
        try {
            User user = this.userRepository.findById(userId).get();
            List<UserContacts> userContacts = this.userContactRepository.findByUserId(user);
            List<UserDTO> userContactsResp = new ArrayList<>();
            userContacts.forEach(userContact -> {
                User u = userContact.getContactUserId();
                UserDTO userDTO = UserDTO.builder()
                        .email(u.getEmail())
                        .id(u.getId())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .profileImage(u.getProfileImage())
                        .build();
                userContactsResp.add(userDTO);
            });
            return UserContactsDTO.builder()
                    .userId(userId)
                    .username(user.getEmail())
                    .contacts(userContactsResp).build();
        } catch (Exception e) {
            logger.info(LOGGER_TAG + " Exception occurred in getUserContacts : " + e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseEntity<?> addUserContactAPI(UserContactMap userContactMap) {
        try {
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
        } catch (Exception e) {
            logger.info(LOGGER_TAG + " Exception occurred in addUserContact : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occurred in addUserContact " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getUserContactsAPI(String userId) {
        try {
            logger.info(LOGGER_TAG + " method getUserContactsAPI called with userId : " + userId);
            if (this.userService.isUserPresent(userId)) {
                logger.info(String.format(LOGGER_TAG + " user with userId : %s exists", userId));
                return ResponseHandler.generateResponse("", HttpStatus.OK, this.getUserContacts(userId));
            }
            logger.info(String.format(LOGGER_TAG + " user with userId : %s not exists", userId));
            return ResponseHandler.generateResponse(String.format("User with userId : %s not exists", userId), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.info(LOGGER_TAG + " Exception occurred in getUserContactsAPI : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occurred " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getGlobalUsersAPI(String userId) {
        try {
            logger.info(LOGGER_TAG + " method getGlobalUsersAPI called with userId : " + userId);
            if (this.userService.isUserPresent(userId)) {
                logger.info(String.format(LOGGER_TAG + " user with userId : %s exists", userId));

                List<User> allUsers = this.userRepository.findAll();
                List<UserContacts> userContacts = this.userContactRepository.findByUserId(this.userRepository.findById(userId).get());
                List<String> contactUserIds = new ArrayList<>();
                userContacts.forEach(contact -> {
                    contactUserIds.add(contact.getContactUserId().getId());
                });
                logger.info(String.format("contactUserIds : %s, cnt : %s", contactUserIds, contactUserIds.size()));

                List<User> filteredUsersList = allUsers.stream()
                        .filter(user -> !contactUserIds.contains(user.getId()) && !Objects.equals(user.getId(), userId))
                        .toList();

                List<UserDTO> userResponse = new ArrayList<>();
                filteredUsersList.forEach(user -> {
                    userResponse.add(UserDTO.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .profileImage(user.getProfileImage()).build());
                });
                return ResponseHandler.generateResponse("", HttpStatus.OK, userResponse);
            }
            logger.info(String.format(LOGGER_TAG + " user with userId : %s not exists", userId));
            return ResponseHandler.generateResponse(String.format("User with userId : %s not exists", userId), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.info(LOGGER_TAG + " Exception occurred in getGlobalUsersAPI : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occurred " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getUserInfo(String userId) {
        try {
            logger.info(LOGGER_TAG + " method getUserInfo called with userId : " + userId);
            if (this.userService.isUserPresent(userId)) {
                User user = this.userRepository.findById(userId).get();

                UserDTO userDTO = UserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .profileImage(user.getProfileImage())
                        .build();

                return ResponseHandler.generateResponse("User details found!!", HttpStatus.OK, userDTO);
            }
            logger.info(String.format(LOGGER_TAG + " user with userId : %s not exists", userId));
            return ResponseHandler.generateResponse(String.format("User with userId : %s not exists", userId), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.info(LOGGER_TAG + " Exception occurred in getGlobalUsersAPI : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occurred " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> deleteUserContactAPI(UserContactMap userContactMap) {
        try {
            logger.info(LOGGER_TAG + " method getUserInfo called with userContactMap : " + userContactMap.toString());
            if (isUserContactExists(userContactMap.getUserId(), userContactMap.getContactId())) {
                List<UserContacts> userContactInfo = this.userContactRepository.findByUserIdAndContactUserId(new User(userContactMap.getUserId()),
                        new User(userContactMap.getContactId()));
                this.userContactRepository.deleteById(userContactInfo.get(0).userContactId);
                return ResponseHandler.generateResponse("contact removed from contacts for provided user!!", HttpStatus.OK, null);
            }
            logger.info(LOGGER_TAG + " userContact not found for requested userId and contactId");
            return ResponseHandler.generateResponse("user contact not found!!", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.info(LOGGER_TAG + " Exception occurred in deleteUserContact : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occurred " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    public boolean isUserContactExists(String userId, String contactId){
        List<UserContacts> resp = this.userContactRepository.findByUserIdAndContactUserId(new User(userId), new User(contactId));
        return !resp.isEmpty();
    }

}
