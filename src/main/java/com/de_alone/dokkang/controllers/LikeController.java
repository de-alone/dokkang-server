package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.BoardLike;
import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.LikePostRequest;
import com.de_alone.dokkang.payload.response.StatusResponse;
import com.de_alone.dokkang.repository.BoardLikeRepository;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Autowired
    BoardPostRepository boardPostRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerLikePost(@Valid @RequestBody LikePostRequest likePostRequest) {
        Long post_id = likePostRequest.getPost_id();
        Long user_id = likePostRequest.getUser_id();

        BoardPost boardPost = boardPostRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        if (boardLikeRepository.findByPostIdAndUserId(boardPost, user).size() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new StatusResponse("error"));
        }

        // Create new boardLike
        BoardLike boardLike = new BoardLike(boardPost, user);
        boardLikeRepository.save(boardLike);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponse("ok"));
    }
}
