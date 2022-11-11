package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.BoardComment;
import com.de_alone.dokkang.models.BoardLike;
import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.payload.request.PostLectureRequest;
import com.de_alone.dokkang.payload.response.PostLecture;
import com.de_alone.dokkang.payload.response.PostLectureResponse;
import com.de_alone.dokkang.repository.BoardCommentRepository;
import com.de_alone.dokkang.repository.BoardLikeRepository;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lecture")
public class PostLectureController {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    BoardPostRepository boardPostRepository;

    @Autowired
    BoardCommentRepository boardCommentRepository;

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @GetMapping("/{lecture_id}/posts")
    public ResponseEntity<?> readPostLectureDetail(@RequestParam(required=false) String jwt, @PathVariable Long lecture_id, @Valid @RequestBody PostLectureRequest postLectureRequest) {
        String before = postLectureRequest.getBefore();
        Integer limit = postLectureRequest.getLimit();

        LocalDateTime dateTime = LocalDateTime.parse(before, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<BoardPost> boardPost = boardPostRepository.findAllByLectureId(lectureRepository.findById(lecture_id).orElseThrow(IllegalArgumentException::new));
        Collections.reverse(boardPost);

        List<PostLecture> posts = new ArrayList<>();
        for(BoardPost post:boardPost) {
            if(dateTime.isAfter(post.getCreated_at().toLocalDateTime())){
                if (limit <= 0) {
                    break;
                }
                else {
                    List<BoardComment> comment_list = boardCommentRepository.findAllByPostId(post);
                    List<BoardLike> like_list = boardLikeRepository.findAllByPostId(post);
                    posts.add(new PostLecture(post.getId(), post.getLectureId().getId(), post.getTitle(), like_list.size() ,comment_list.size()));
                    before = post.getCreated_at().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    limit--;
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new PostLectureResponse("ok", posts, before));
    }
}

