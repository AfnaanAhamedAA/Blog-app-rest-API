package com.myblogrestapi.controller;


import com.myblogrestapi.entity.Roles;
import com.myblogrestapi.entity.User;
import com.myblogrestapi.payload.UserDto;
import com.myblogrestapi.repository.rolesRepositories;
import com.myblogrestapi.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private userRepository userRepo;

    @Autowired
    public AuthController(userRepository userRepo,
                          rolesRepositories rolesRepositories) {
        this.userRepo = userRepo;
        this.rolesRepositories = rolesRepositories;
    }


    private PasswordEncoder passwordEncoder;


    @Autowired
    private  AuthenticationManager authenticationManager;
    private com.myblogrestapi.repository.rolesRepositories rolesRepositories;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signIn")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDto userDto){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return  new ResponseEntity<>("User signed In Successfully",HttpStatus.OK);
    }


    @PostMapping("/signUp")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(userDto.getUsername());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Roles roles = rolesRepositories.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        User savedUser = userRepo.save(user);
        return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
    }


    public class JWTAuthResponse {
        private String accessToken;
        private String tokenType = "Bearer";

        public JWTAuthResponse(String accessToken) {
            this.accessToken = accessToken;
        }
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }
    }



}


