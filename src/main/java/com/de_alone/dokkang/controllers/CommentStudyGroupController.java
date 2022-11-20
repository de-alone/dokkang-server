package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.CreateCommentStudyGroupRequest;
import com.de_alone.dokkang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/studygroup-comment")
public class CommentStudyGroupController {
    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    StudyGroupCommentRepository studyGroupCommentRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerCommentStudyGroup(@Valid @RequestBody CreateCommentStudyGroupRequest createCommentStudyGroupRequest) {
        Long studygroup_id = createCommentStudyGroupRequest.getStudygroup_id();
        Long user_id = createCommentStudyGroupRequest.getUser_id();
        Date created_at = Timestamp.valueOf(LocalDateTime.now());
        String content = createCommentStudyGroupRequest.getContent();

        StudyGroupPost studyGroupPost = studyGroupRepository.findById(studygroup_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        // Create new studyGroupComment
        StudyGroupComment studyGroupComment = new StudyGroupComment(studyGroupPost, user, created_at, content);
        StudyGroupComment savedStudyGroupComment = studyGroupCommentRepository.save(studyGroupComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "ok", "comment_id", savedStudyGroupComment.getId()));
    }
}
