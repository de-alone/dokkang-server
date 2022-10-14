package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.SignupRequest;
import com.de_alone.dokkang.payload.response.ResponseHandler;
import com.de_alone.dokkang.repository.UserRepository;
import com.de_alone.dokkang.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        //TODO : check logics
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
//        }
        String email_address = signUpRequest.getEmail();
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseHandler.generateResponse("User id already exist", HttpStatus.CONFLICT);
        }
        //TODO : Check Email Acceptance(format)


        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));


        userRepository.save(user);

        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK);
    }

}