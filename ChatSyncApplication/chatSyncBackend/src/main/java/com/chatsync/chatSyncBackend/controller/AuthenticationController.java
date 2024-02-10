package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.config.jwtConfig.JwtUtils;
import com.chatsync.chatSyncBackend.exception.InvalidCredentialsException;
import com.chatsync.chatSyncBackend.exception.UserDisabledException;
import com.chatsync.chatSyncBackend.exception.UserNotFoundException;
import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.repostiroy.UserRepository;
import com.chatsync.chatSyncBackend.service.Impl.UserDetailsServiceImpl;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import com.chatsync.chatSyncBackend.utils.payloads.JwtRequest;
import com.chatsync.chatSyncBackend.utils.payloads.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    // generate token
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest request) throws Exception {
        try{
            authenticate(request.getUsername(), request.getPassword());
        }catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, userDetails));
    }

    private void authenticate(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e) {
            throw new UserDisabledException("USER_DISABLED "+e.getMessage());
        }catch (BadCredentialsException e){
            throw new InvalidCredentialsException("Invalid Credentials!! "+e.getMessage());
        }
    }

    /*
        Returns detail of Current loggedIn user
    */
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }

}
