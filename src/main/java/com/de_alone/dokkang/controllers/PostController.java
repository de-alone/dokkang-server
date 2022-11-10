package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.CreatePostRequest;
import com.de_alone.dokkang.payload.response.StatusResponse;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.LectureRepository;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    BoardPostRepository boardPostRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreatePostRequest createPostRequest) {
        Long lecture_id = createPostRequest.getLecture_id();
        Long user_id = createPostRequest.getUser_id();
        String title = createPostRequest.getTitle();
        String content = createPostRequest.getContent();

        Lecture lecture = lectureRepository.findById(lecture_id).get();
        User user = userRepository.findById(user_id).get();

        // Create new post
        BoardPost boardPost = new BoardPost(lecture, user, title, content);
        boardPostRepository.save(boardPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponse("ok"));
    }
}


