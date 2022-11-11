package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.CreatePostRequest;
import com.de_alone.dokkang.payload.response.Comment;
import com.de_alone.dokkang.payload.response.PostResponse;
import com.de_alone.dokkang.payload.response.StatusResponse;
import com.de_alone.dokkang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Autowired
    BoardCommentRepository boardCommentRepository;

    @PostMapping
    public ResponseEntity<?> registerPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        Long lecture_id = createPostRequest.getLecture_id();
        Long user_id = createPostRequest.getUser_id();
        String title = createPostRequest.getTitle();
        String content = createPostRequest.getContent();

        Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        // Create new post
        BoardPost boardPost = new BoardPost(lecture, user, title, content);
        boardPostRepository.save(boardPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponse("ok"));
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<?> readPostDetail(@RequestParam(required=false) String jwt, @PathVariable Long post_id) {
        BoardPost boardPost = boardPostRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        List<BoardLike> like_list = boardLikeRepository.findAllByPostId(boardPost);
        List<BoardComment> comment_list = boardCommentRepository.findAllByPostId(boardPost);

        Long lecture_id = boardPost.getLectureId().getId();
        Long user_id = boardPost.getUserId().getId();
        String username = boardPost.getUserId().getUsername();
        String title = boardPost.getTitle();
        String content = boardPost.getContent();
        String created_at = String.valueOf(boardPost.getCreated_at());
        Integer num_likes = like_list.size();

        // Create comments
        List<Comment> comments = new ArrayList<>();
        for(BoardComment comment:comment_list) {
            comments.add(new Comment(comment.getId(), comment.getUserId().getId(), comment.getContent(), String.valueOf(comment.getCreated_at())));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new PostResponse("ok", post_id, lecture_id, user_id, username, title, content, created_at, num_likes, comments));
    }
}


