package com.chatsyncapp.chatSyncApp.service.impl;

import com.chatsyncapp.chatSyncApp.dto.UserDTO;
import com.chatsyncapp.chatSyncApp.dto.UserRegistrationDto;
import com.chatsyncapp.chatSyncApp.model.Role;
import com.chatsyncapp.chatSyncApp.model.User;
import com.chatsyncapp.chatSyncApp.repository.RoleRepository;
import com.chatsyncapp.chatSyncApp.repository.UserRepository;
import com.chatsyncapp.chatSyncApp.service.UserService;
import com.chatsyncapp.chatSyncApp.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String LOGGER_TAG = "UserServiceImpl : ";
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(UserRegistrationDto registrationDto) {
        Role role;
        if (this.roleRepository.existsByName("ROLE_USER")){
            role = this.roleRepository.findByName("ROLE_USER");
        }else{
            role = new Role("ROLE_USER");
        }

        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), List.of(role));

        userRepository.save(user);
    }

    /*
     * Method for getting All Users of System
     */
    @Override
    public ResponseEntity<?> getAllUsers() {
        try {
            logger.info(LOGGER_TAG + " method called getAllUsers ");
            List<User> listOfUsers = this.userRepository.findAll();
            List<UserDTO> respUsers = new ArrayList<>();
            listOfUsers.forEach(user -> {
                UserDTO u = UserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .profileImage(user.getProfileImage())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build();
                respUsers.add(u);
            });
            return ResponseHandler.generateResponse("", HttpStatus.OK, respUsers);
        }catch (Exception e){
            logger.info(LOGGER_TAG + " Exception occurred in getAllUsers : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public String getLoggedInUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User userInfo = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        logger.info(" userinfo : " + userInfo.toString());
        return userInfo.getUsername();
    }


}
