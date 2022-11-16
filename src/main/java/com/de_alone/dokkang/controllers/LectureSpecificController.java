package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.response.LecturePostResponse;
import com.de_alone.dokkang.payload.response.LectureStudyGroupResponse;
import com.de_alone.dokkang.payload.response.PostLecture;
import com.de_alone.dokkang.payload.response.StudyGroupLecture;
import com.de_alone.dokkang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lecture")
public class LectureSpecificController {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    BoardPostRepository boardPostRepository;

    @Autowired
    BoardCommentRepository boardCommentRepository;

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    StudyGroupCommentRepository studyGroupCommentRepository;

    @Autowired
    StudyGroupLikeRepository studyGroupLikeRepository;

    @Autowired
    StudyGroupParticipationRepository studyGroupParticipationRepository;

    @GetMapping("/{lecture_id}/posts")
    public ResponseEntity<?> readLecturePostDetail(@RequestParam(required=false) String jwt, @PathVariable Long lecture_id, @RequestParam Integer limit, @RequestParam Optional<String> before) {
        LocalDateTime dateTime = before.isPresent() ? LocalDateTime.parse(before.get(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : LocalDateTime.now();

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
                    posts.add(new PostLecture(post.getId(), post.getLectureId().getId(), post.getTitle(), like_list.size() ,comment_list.size(), post.getCreated_at().toString()));
                    before = Optional.of(post.getCreated_at().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    limit--;
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new LecturePostResponse("ok", posts, before));
    }

    @GetMapping("/{lecture_id}/studygroups")
    public ResponseEntity<?> readLectureStudyGroupDetail(@RequestParam(required=false) String jwt, @PathVariable Long lecture_id, @RequestParam Integer limit, @RequestParam Optional<String> before) {
        LocalDateTime dateTime = before.isPresent() ? LocalDateTime.parse(before.get(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : LocalDateTime.now();

        List<StudyGroupPost> studyGroupPost = studyGroupRepository.findAllByLectureId(lectureRepository.findById(lecture_id).orElseThrow(IllegalArgumentException::new));
        Collections.reverse(studyGroupPost);

        List<StudyGroupLecture> studygroups = new ArrayList<>();
        for(StudyGroupPost post:studyGroupPost) {
            if(dateTime.isAfter(post.getCreated_at().toLocalDateTime())){
                if (limit <= 0) {
                    break;
                }
                else {
                    List<StudyGroupComment> comment_list = studyGroupCommentRepository.findAllByPostId(post);
                    List<StudyGroupLike> like_list = studyGroupLikeRepository.findAllByPostId(post);
                    List<StudyGroupParticipation> participants_list = studyGroupParticipationRepository.findAllByPostId(post);
                    Boolean opened = ((participants_list.size() + 1) < post.getStudycapacity()); // 참여자 + 개설자 < 정원
                    studygroups.add(new StudyGroupLecture(post.getId(), post.getLectureId().getId(), post.getTitle(), like_list.size() ,comment_list.size(), post.getCreated_at().toString(), opened));
                    before = Optional.of(post.getCreated_at().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    limit--;
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new LectureStudyGroupResponse("ok", studygroups, before));
    }
}

