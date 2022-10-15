package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.SignupRequest;
import com.de_alone.dokkang.payload.response.ResponseHandler;
import com.de_alone.dokkang.repository.UserRepository;
import com.de_alone.dokkang.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        if (userRepository.existsByUsername(username)) {
           return ResponseHandler.generateResponse("username already exists!", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseHandler.generateResponse("emgail address already exists!", HttpStatus.CONFLICT);
        }

        // Create new user's account
        User user = new User(username, email, encoder.encode(password));
        userRepository.save(user);

        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK);
    }

}
