package com.chatsyncapp.chatSyncApp.controller;

import com.chatsyncapp.chatSyncApp.dto.UserDTO;
import com.chatsyncapp.chatSyncApp.model.User;
import com.chatsyncapp.chatSyncApp.repository.UserRepository;
import com.chatsyncapp.chatSyncApp.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class RestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/global-users")
    public ResponseEntity<?> getAllActiveUsers(){
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
    }

}
