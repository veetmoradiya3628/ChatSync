package com.chatsyncserver.chatSyncServer.controller;

import com.chatsyncserver.chatSyncServer.payload.ResponseHandler;
import com.chatsyncserver.chatSyncServer.payload.request.LoginRequest;
import com.chatsyncserver.chatSyncServer.payload.request.SignupRequest;
import com.chatsyncserver.chatSyncServer.service.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
           return this.authService.signIn(loginRequest);
        }catch (Exception e){
            return ResponseHandler.generateResponse("Error occurred while tried log in user "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try{
            return this.authService.signUp(signUpRequest);
        }catch (Exception e){
            return ResponseHandler.generateResponse("Error occurred while tried sign up user "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

}
