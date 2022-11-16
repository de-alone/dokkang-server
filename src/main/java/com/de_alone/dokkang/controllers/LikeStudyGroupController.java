package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.LikeStudyGroupRequest;
import com.de_alone.dokkang.payload.response.StatusResponse;
import com.de_alone.dokkang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/studygroup-like")
public class LikeStudyGroupController {
    @Autowired
    StudyGroupLikeRepository studyGroupLikeRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerLikeStudyGroup(@Valid @RequestBody LikeStudyGroupRequest likeStudyGroupRequest) {
        Long studygroup_id = likeStudyGroupRequest.getStudygroup_id();
        Long user_id = likeStudyGroupRequest.getUser_id();

        StudyGroupPost studyGroupPost = studyGroupRepository.findById(studygroup_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        if (studyGroupLikeRepository.findByPostIdAndUserId(studyGroupPost, user).size() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new StatusResponse("error"));
        }

        // Create new studyGroupLike
        StudyGroupLike studyGroupLike = new StudyGroupLike(studyGroupPost, user);
        studyGroupLikeRepository.save(studyGroupLike);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponse("ok"));
    }
}
