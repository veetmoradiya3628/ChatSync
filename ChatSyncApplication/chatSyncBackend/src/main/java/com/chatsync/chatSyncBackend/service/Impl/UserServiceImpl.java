package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.dto.UserDto;
import com.chatsync.chatSyncBackend.model.Role;
import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.model.UserRole;
import com.chatsync.chatSyncBackend.repostiroy.UserRepository;
import com.chatsync.chatSyncBackend.service.RoleService;
import com.chatsync.chatSyncBackend.service.UserService;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final String LOG_TAG = "UserServiceImpl";

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private RoleService roleService;

    private EmailService emailService;


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }


    /*
        createNewUserAccount: UserDto - used to register new user
     */
    @Override
    public ResponseEntity<?> createNewUserAccount(UserDto userDto) {
        try {
            logger.info(LOG_TAG + " createNewUserAccount with " + userDto.toString());

            // check for user already exists or not (username, email)
            if (!isUserExists(userDto.getUsername(), userDto.getEmail())) {
                // validate required parameters - later
                // profile picture upload and url addition - later
                // mail for verification token and verification link - later


                // user roles validation - as of now one role in the request and also it can be ROLE_USER, ROLE_ADMIN
                String reqRole = userDto.getRoles().get(0).toUpperCase();
                if (!reqRole.equals("ROLE_USER") && !reqRole.equals("ROLE_ADMIN")) {
                    logger.info("Invalid user role passed as " + reqRole);
                    return ResponseHandler.generateResponse("Invalid user role passed!", HttpStatus.NOT_ACCEPTABLE, null);
                }

                Role role = this.roleService.getRoleByRoleName(reqRole);
                logger.info(" role info for passed reqRole is : " + role.toString());

                // process for storing that user

                User user = User.builder()
                        .username(userDto.getUsername())
                        .password(this.bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .email(userDto.getEmail())
                        .isActive(false)
                        .phoneNo(userDto.getPhoneNo())
                        .verificationToken(this.generateVerificationToken())
                        .resetPasswordOtp(this.optGenerator(6))
                        .profileImage(null)
                        .build();

                Set<UserRole> roles = new HashSet<>();
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                roles.add(userRole);

                user.setUserRoles(roles);

                logger.info("generated user body to store : " + user.toString());

                User _savedUser = this.userRepository.save(user);
                return ResponseHandler.generateResponse("User registered successfully!", HttpStatus.CREATED, _savedUser);

            }
            return ResponseHandler.generateResponse("User already exists with username or email", HttpStatus.NOT_ACCEPTABLE, null);
        } catch (Exception e) {
            logger.info("Exception occurred in the function resetUserPassword : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    /*
        requestActivationDetails: emailId - user mail for whom activation details is requested.
     */
    @Override
    public ResponseEntity<?> requestActivationDetails(String emailId) {
        try{
            if(this.userRepository.existsByEmail(emailId)){
                User user = this.userRepository.findByEmail(emailId);
                logger.info("user details : " + user.toString());

                // activation details mail sent logic

                String mailSubject = "Chat Sync Activation & Verification Mail";
                String content = "Hi " + user.getUsername() + ",\nThanks for registering your self on ChatSync, below is link to active your account."
                        + "\n\nActivation link : http://localhost:4200/activate-account/" + user.getEmail() + "/" + user.getVerificationToken();

                emailService.sendEmail(emailId, mailSubject, content);

                return ResponseHandler.generateResponse("Activation details sent to registered mailId", HttpStatus.OK, null);

            }
            logger.info("user with provided email id not exists!! : " + emailId);
            return ResponseHandler.generateResponse("User with provided email id not exists!!", HttpStatus.NOT_FOUND, null);

        } catch (Exception e) {
            logger.info("Exception occurred in the function requestActivationDetails : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    /*
        activateUserAccount: emailId - user mail for whom have to activate the account, activationToken - activation token
     */
    @Override
    public ResponseEntity<?> activateUserAccount(String emailId, String activationToken) {
        try{
            if(this.userRepository.existsByEmailAndVerificationToken(emailId, activationToken)){
                User user = this.userRepository.findByEmail(emailId);
                user.setActive(true);
                userRepository.save(user);
                logger.info("user with emailId : " + emailId + " activated!!");
                return ResponseHandler.generateResponse("Account activated successfully!!, please proceed with login", HttpStatus.OK, null);
            }
            logger.info("user with provided email id not exists!! : " + emailId);
            return ResponseHandler.generateResponse("Invalid Activation request!!!, please try again", HttpStatus.NOT_FOUND, null);
        }catch (Exception e){
            logger.info("Exception occurred in the function activateUserAccount : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> getUserInfoByUserId(String userId) {
        try {
            logger.info(LOG_TAG + " getUserInfoByUserId called with userId : " + userId);
            if (isUserExistsById(userId)) {
                User user = this.userRepository.findById(userId).get();
                List<String> userRole = new ArrayList<>();
                user.getUserRoles().forEach(roleInfo -> {
                    userRole.add(roleInfo.getRole().getRoleName());
                });
                UserDto respUser = UserDto.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .profileImage(user.getProfileImage())
                        .username(user.getUsername())
                        .isActive(user.isActive())
                        .phoneNo(user.getPhoneNo())
                        .createdAt(user.getCreatedAt())
                        .roles(userRole)
                        .updatedAt(user.getUpdatedAt())
                        .build();

                return ResponseHandler.generateResponse("user details", HttpStatus.OK, respUser);
            } else {
                logger.info(LOG_TAG + " user not exists with userID : " + userId);
                return ResponseHandler.generateResponse("User not exists with userID : " + userId, HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.info("Exception occurred in the function getUserInfoByUserId : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    protected boolean isUserExists(String username, String email) {
        return this.userRepository.existsByUsername(username) || this.userRepository.existsByEmail(email);
    }

    protected String generateVerificationToken() {
        UUID randomUUID;
        do {
            randomUUID = UUID.randomUUID();
        } while (this.userRepository.existsByVerificationToken(UUID.randomUUID().toString()));
        return randomUUID.toString();
    }

    protected String optGenerator(int length) {
        String otp;
        do {
            StringBuilder otpBuilder = new StringBuilder();
            // Generate each digit of the OTP
            for (int i = 0; i < length; i++) {
                int digit = new Random().nextInt(10); // Generates a random digit between 0 and 9
                otpBuilder.append(digit);
            }
            otp = otpBuilder.toString();
        } while (this.userRepository.existsByResetPasswordOtp(otp));
        return otp;
    }

    public boolean isUserExistsById(String userId) {
        return this.userRepository.existsById(userId);
    }

    public void updateUserProfilePath(String userId, String profilePath) {
        logger.info(LOG_TAG + " updateUserProfilePath called with " + userId + " path: " + profilePath);
        User user = this.userRepository.findById(userId).get();
        user.setProfileImage(profilePath);
        logger.info(LOG_TAG + " user object to be saved : " + user);
        this.userRepository.save(user);
    }

    public User getUserByUserId(String userId){
        return this.userRepository.findById(userId).get();
    }
}
