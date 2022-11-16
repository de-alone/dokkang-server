package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.StudyGroupParticipation;
import com.de_alone.dokkang.models.StudyGroupPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.LikeParticipateStudyGroupRequest;
import com.de_alone.dokkang.payload.response.StatusResponse;
import com.de_alone.dokkang.repository.StudyGroupParticipationRepository;
import com.de_alone.dokkang.repository.StudyGroupRepository;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/studygroup-like")
public class ParticipateStudyGroupController {
    @Autowired
    StudyGroupParticipationRepository studyGroupParticipationRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerParticipateStudyGroup(@Valid @RequestBody LikeParticipateStudyGroupRequest likeParticipateStudyGroupRequest) {
        Long studygroup_id = likeParticipateStudyGroupRequest.getStudygroup_id();
        Long user_id = likeParticipateStudyGroupRequest.getUser_id();

        StudyGroupPost studyGroupPost = studyGroupRepository.findById(studygroup_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        if (studyGroupParticipationRepository.findByPostIdAndUserId(studyGroupPost, user).size() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new StatusResponse("error"));
        }

        List<StudyGroupParticipation> participants_list = studyGroupParticipationRepository.findAllByPostId(studyGroupPost);
        if ((participants_list.size() + 1) >= studyGroupPost.getStudycapacity()) {
            return ResponseEntity.status(HttpStatus.LOCKED).body(new StatusResponse("error"));
        }

        // Create new studyGroupParticipation
        StudyGroupParticipation studyGroupParticipation = new StudyGroupParticipation(studyGroupPost, user);
        studyGroupParticipationRepository.save(studyGroupParticipation);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponse("ok"));
    }
}
