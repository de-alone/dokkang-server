package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.StudyGroupPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.CreateStudyGroupRequest;
import com.de_alone.dokkang.repository.LectureRepository;
import com.de_alone.dokkang.repository.StudyGroupRepository;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        studyGroupRepository.save(studyGroupPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "ok", "studygroup_id", studyGroupPost.getId()));
    }
}
