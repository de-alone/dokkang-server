package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.CreateStudyGroupRequest;
import com.de_alone.dokkang.payload.response.Comment;
import com.de_alone.dokkang.payload.response.StudyGroupResponse;
import com.de_alone.dokkang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/studygroup")
public class StudyGroupController {
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    StudyGroupLikeRepository studyGroupLikeRepository;

    @Autowired
    StudyGroupCommentRepository studyGroupCommentRepository;

    @Autowired
    StudyGroupParticipationRepository studyGroupParticipationRepository;

    @PostMapping
    public ResponseEntity<?> registerStudyGroup(@Valid @RequestBody CreateStudyGroupRequest createStudyGroupRequest) {
        Long lecture_id = createStudyGroupRequest.getLecture_id();
        Long user_id = createStudyGroupRequest.getUser_id();
        String title = createStudyGroupRequest.getTitle();
        String content = createStudyGroupRequest.getContent();
        String studytime = createStudyGroupRequest.getStudytime();
        String studyplace = createStudyGroupRequest.getStudyplace();
        Integer studycapacity = createStudyGroupRequest.getStudycapacity();

        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        // Create new study group
        StudyGroupPost studyGroupPost = new StudyGroupPost(lecture, user, title, content, studytime, studyplace, studycapacity);
        StudyGroupPost savedStudyGroupPost = studyGroupRepository.save(studyGroupPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "ok", "studygroup_id", savedStudyGroupPost.getId()));
    }

    @GetMapping("/{studygroup_id}")
    public ResponseEntity<?> readStudyGroupDetail(@RequestParam(required=false) String jwt, @PathVariable Long studygroup_id) {
        StudyGroupPost studyGroupPost = studyGroupRepository.findById(studygroup_id).orElseThrow(IllegalArgumentException::new);
        List<StudyGroupLike> like_list = studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost);
        List<StudyGroupParticipation> participants_list = studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost);
        List<StudyGroupComment> comment_list = studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost);

        Long lecture_id = studyGroupPost.getLectureId().getId();
        Long user_id = studyGroupPost.getUserId().getId();
        String username = studyGroupPost.getUserId().getUsername();
        String title = studyGroupPost.getTitle();
        String content = studyGroupPost.getContent();
        String created_at = String.valueOf(studyGroupPost.getCreated_at());
        String studytime = studyGroupPost.getStudytime();
        String studyplace = studyGroupPost.getStudyplace();
        Integer studycapacity = studyGroupPost.getStudycapacity();
        Integer num_likes = like_list.size();

        // get username list
        List<String> participants = new ArrayList<>();
        participants.add(username);
        for(StudyGroupParticipation participation:participants_list) {
            participants.add(participation.getUserId().getUsername());
        }

        // Create comments
        List<Comment> comments = new ArrayList<>();
        for(StudyGroupComment comment:comment_list) {
            comments.add(new Comment(comment.getId(), comment.getUserId().getId(), comment.getContent(), String.valueOf(comment.getCreated_at())));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new StudyGroupResponse("ok", studygroup_id, lecture_id, user_id, username, title, content, created_at, num_likes, comments, participants, studytime, studyplace, studycapacity));
    }
}
