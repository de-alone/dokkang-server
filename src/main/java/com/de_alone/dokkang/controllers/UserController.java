package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.SignupRequest;
import com.de_alone.dokkang.payload.response.LectureResponse;
import com.de_alone.dokkang.payload.response.SignupResponse;
import com.de_alone.dokkang.repository.LectureRepository;
import com.de_alone.dokkang.repository.UserLectureRepository;
import com.de_alone.dokkang.repository.UserRepository;
import com.de_alone.dokkang.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLectureRepository userlectureRepository;

    @Autowired
    LectureRepository lectureRepository;

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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SignupResponse("Username is already taken!"));
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SignupResponse("Email is already in use!"));
        }

        // Create new user's account
        User user = new User(username, email, encoder.encode(password));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SignupResponse("User registered successfully!"));
    }

    @GetMapping("/{user_id}/lectures")
    public ResponseEntity<?> getUserLectures(@RequestParam(required = false) String jwt, @PathVariable Long user_id) {

        List<Lecture> lectures = lectureRepository.findAllById(userlectureRepository.findLectureById(user_id));
        return ResponseEntity.status(HttpStatus.CREATED).body(new LectureResponse("ok", lectures));
    }

}
