package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.BoardComment;
import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.CreateCommentRequest;
import com.de_alone.dokkang.repository.BoardCommentRepository;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    BoardPostRepository boardPostRepository;

    @Autowired
    BoardCommentRepository boardCommentRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> registerLikePost(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        Long post_id = createCommentRequest.getPost_id();
        Long user_id = createCommentRequest.getUser_id();
        String content = createCommentRequest.getContent();

        BoardPost boardPost = boardPostRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        // Create new boardComment
        BoardComment boardComment = new BoardComment(boardPost, user, content);
        boardCommentRepository.save(boardComment);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "ok", "comment_id", boardComment.getId()));
    }
}
