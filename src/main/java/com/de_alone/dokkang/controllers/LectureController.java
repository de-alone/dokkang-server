package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.payload.response.LectureResponse;
import com.de_alone.dokkang.repository.LectureRepository;
import com.de_alone.dokkang.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lectures")
public class LectureController {
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> getLectures(@RequestParam(required = false) String jwt) {

        List<Lecture> lectures = lectureRepository.findAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(new LectureResponse("ok", lectures));
    }
}
