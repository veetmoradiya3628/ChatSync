package com.chatsyncserver.chatSyncServer.service;

import com.chatsyncserver.chatSyncServer.jwt.JwtUtils;
import com.chatsyncserver.chatSyncServer.model.AppRoles;
import com.chatsyncserver.chatSyncServer.model.Role;
import com.chatsyncserver.chatSyncServer.model.User;
import com.chatsyncserver.chatSyncServer.payload.ResponseHandler;
import com.chatsyncserver.chatSyncServer.payload.request.LoginRequest;
import com.chatsyncserver.chatSyncServer.payload.request.SignupRequest;
import com.chatsyncserver.chatSyncServer.payload.response.JwtResponse;
import com.chatsyncserver.chatSyncServer.repository.RoleRepository;
import com.chatsyncserver.chatSyncServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public ResponseEntity<?> signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse response = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
        return ResponseHandler.generateResponse("LoggedIn Successfully", HttpStatus.OK, response);
    }

    public ResponseEntity<?> signUp(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseHandler.generateResponse("Username already exists!!", HttpStatus.CONFLICT, null);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseHandler.generateResponse("Email already exists!!", HttpStatus.CONFLICT, null);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(AppRoles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(AppRoles.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(AppRoles.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseHandler.generateResponse("User registered successfully!",
                HttpStatus.OK,
                user);
    }
}
