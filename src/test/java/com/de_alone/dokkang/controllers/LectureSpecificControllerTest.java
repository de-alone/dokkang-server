package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.repository.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class LectureSpecificControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LectureRepository lectureRepository;

    @MockBean
    BoardPostRepository boardPostRepository;

    @MockBean
    BoardCommentRepository boardCommentRepository;

    @MockBean
    BoardLikeRepository boardLikeRepository;
    @MockBean
    StudyGroupRepository studyGroupRepository;

    @MockBean
    StudyGroupCommentRepository studyGroupCommentRepository;
    @MockBean
    StudyGroupLikeRepository studyGroupLikeRepository;
    @MockBean
    StudyGroupParticipationRepository studyGroupParticipationRepository;

    Long lecture_id = 101L;
    Lecture lecture = new Lecture(lecture_id, "SWE3033", "Software Engineering", "Cha");

    User user1 = new User(1L, "username", "email@email.com", "password");
    User user2 = new User(2L, "user", "email@email.com", "password");

    BoardPost boardPost1 = new BoardPost(1L, lecture, user1, "test of board1", "content");

    BoardPost boardPost2 = new BoardPost(2L, lecture, user2, "test of board2", "content");

    StudyGroupPost studyGroupPost
            = new StudyGroupPost(11L, lecture, user1, "title", "content", "studytime", "studyplace", 12);
    StudyGroupPost studyGroupPost_alone
            = new StudyGroupPost(11L, lecture, user1, "title", "content", "studytime", "studyplace", 1);

    BoardComment boardComment1
            = new BoardComment(boardPost1, user1, new Date(), "content");
    BoardComment boardComment2
            = new BoardComment(boardPost1, user1, new Date(), "content");

    BoardLike boardLike
            = new BoardLike(1L, user2, boardPost1);
    BoardLike boardLike2
            = new BoardLike(2L, user1, boardPost2);

    @DisplayName("get Post Lecture Detail")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testReadLecturePostDetail() throws Exception {
        boardPost1.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        boardPost2.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<BoardPost> listOfBoardPost = new ArrayList<>();
        listOfBoardPost.add(boardPost1);
        listOfBoardPost.add(boardPost2);

        given(boardPostRepository.findById(boardPost1.getId())).willReturn(Optional.of(boardPost1));
        given(boardPostRepository.findById(boardPost2.getId())).willReturn(Optional.of(boardPost2));

        given(lectureRepository.findById(lecture_id)).willReturn(Optional.of(lecture));
        given(boardPostRepository.findAllByLectureId(lecture)).willReturn(listOfBoardPost);

        when(boardCommentRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardComment1, boardComment2));
        when(boardCommentRepository.findAllByPostId(boardPost2)).thenReturn(List.of());

        when(boardLikeRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardLike));
        when(boardLikeRepository.findAllByPostId(boardPost2)).thenReturn(List.of(boardLike2));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/posts")
                .param("limit", String.valueOf(20))
                .param("before", "2022-12-22 12:34:56");

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testReadLecturePostDetail_zerolimit() throws Exception {
        boardPost1.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        boardPost2.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<BoardPost> listOfBoardPost = new ArrayList<>();
        listOfBoardPost.add(boardPost1);
        listOfBoardPost.add(boardPost2);

        given(boardPostRepository.findById(boardPost1.getId())).willReturn(Optional.of(boardPost1));
        given(boardPostRepository.findById(boardPost2.getId())).willReturn(Optional.of(boardPost2));

        given(lectureRepository.findById(lecture_id)).willReturn(Optional.of(lecture));
        given(boardPostRepository.findAllByLectureId(lecture)).willReturn(listOfBoardPost);

        when(boardCommentRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardComment1, boardComment2));
        when(boardCommentRepository.findAllByPostId(boardPost2)).thenReturn(List.of());

        when(boardLikeRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardLike));
        when(boardLikeRepository.findAllByPostId(boardPost2)).thenReturn(List.of(boardLike2));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/posts")
                .param("before", "2022-12-22 12:34:56")
                .param("limit", String.valueOf(0));

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testReadLecturePostDetail_nobefore() throws Exception {
        boardPost1.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        boardPost2.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<BoardPost> listOfBoardPost = new ArrayList<>();
        listOfBoardPost.add(boardPost1);
        listOfBoardPost.add(boardPost2);

        given(boardPostRepository.findById(boardPost1.getId())).willReturn(Optional.of(boardPost1));
        given(boardPostRepository.findById(boardPost2.getId())).willReturn(Optional.of(boardPost2));

        given(lectureRepository.findById(lecture_id)).willReturn(Optional.of(lecture));
        given(boardPostRepository.findAllByLectureId(lecture)).willReturn(listOfBoardPost);

        when(boardCommentRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardComment1, boardComment2));
        when(boardCommentRepository.findAllByPostId(boardPost2)).thenReturn(List.of());

        when(boardLikeRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardLike));
        when(boardLikeRepository.findAllByPostId(boardPost2)).thenReturn(List.of(boardLike2));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/posts")
                .param("limit", String.valueOf(12));

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testReadLecturePostDetail_iscreatedAfter() throws Exception {
        boardPost1.setCreated_at(Timestamp.valueOf(LocalDateTime.of(2033, 12, 28, 12, 31)));
        boardPost2.setCreated_at(Timestamp.valueOf(LocalDateTime.of(2033, 12, 28, 12, 31)));

        List<BoardPost> listOfBoardPost = new ArrayList<>();
        listOfBoardPost.add(boardPost1);
        listOfBoardPost.add(boardPost2);

        given(boardPostRepository.findById(boardPost1.getId())).willReturn(Optional.of(boardPost1));
        given(boardPostRepository.findById(boardPost2.getId())).willReturn(Optional.of(boardPost2));

        given(lectureRepository.findById(lecture_id)).willReturn(Optional.of(lecture));
        given(boardPostRepository.findAllByLectureId(lecture)).willReturn(listOfBoardPost);

        when(boardCommentRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardComment1, boardComment2));
        when(boardCommentRepository.findAllByPostId(boardPost2)).thenReturn(List.of());

        when(boardLikeRepository.findAllByPostId(boardPost1)).thenReturn(List.of(boardLike));
        when(boardLikeRepository.findAllByPostId(boardPost2)).thenReturn(List.of(boardLike2));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/posts")
                .param("limit", String.valueOf(12));

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("get Post Lecture Detail")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testLectureStudyGroupDetail() throws Exception {
        studyGroupPost.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        studyGroupPost_alone.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<StudyGroupPost> listOfStudyGroupPost = new ArrayList<>();
        listOfStudyGroupPost.add(studyGroupPost);
        listOfStudyGroupPost.add(studyGroupPost_alone);

        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.ofNullable(lecture));
        given(studyGroupRepository.findAllByLectureId(lecture)).willReturn(listOfStudyGroupPost);
        given(studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupComment(0L, studyGroupPost, user1, studyGroupPost.getCreated_at(), "content"),
                new StudyGroupComment(1L, studyGroupPost, user2, studyGroupPost.getCreated_at(), "content2")
        ));
        given(studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupLike(10L, user1, studyGroupPost),
                new StudyGroupLike(11L, user2, studyGroupPost)
        ));
        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupParticipation(1000L, user1, studyGroupPost),
                new StudyGroupParticipation(1001L, user2, studyGroupPost)
        ));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/studygroups")
                .param("limit", String.valueOf(20))
                .param("before", "2022-12-22 12:34:56");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testLectureStudyGroupDetail_zerolimit() throws Exception {
        studyGroupPost.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        studyGroupPost_alone.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<StudyGroupPost> listOfStudyGroupPost = new ArrayList<>();
        listOfStudyGroupPost.add(studyGroupPost);
        listOfStudyGroupPost.add(studyGroupPost_alone);

        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.ofNullable(lecture));
        given(studyGroupRepository.findAllByLectureId(lecture)).willReturn(listOfStudyGroupPost);
        given(studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupComment(0L, studyGroupPost, user1, studyGroupPost.getCreated_at(), "content"),
                new StudyGroupComment(1L, studyGroupPost, user2, studyGroupPost.getCreated_at(), "content2")
        ));
        given(studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupLike(10L, user1, studyGroupPost),
                new StudyGroupLike(11L, user2, studyGroupPost)
        ));
        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupParticipation(1000L, user1, studyGroupPost),
                new StudyGroupParticipation(1001L, user2, studyGroupPost)
        ));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/studygroups")
                .param("before", "2022-12-22 12:34:56")
                .param("limit", String.valueOf(0));

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testLectureStudyGroupDetail_nobefore() throws Exception {
        studyGroupPost.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        studyGroupPost_alone.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<StudyGroupPost> listOfStudyGroupPost = new ArrayList<>();
        listOfStudyGroupPost.add(studyGroupPost);
        listOfStudyGroupPost.add(studyGroupPost_alone);

        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.ofNullable(lecture));
        given(studyGroupRepository.findAllByLectureId(lecture)).willReturn(listOfStudyGroupPost);
        given(studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupComment(0L, studyGroupPost, user1, studyGroupPost.getCreated_at(), "content"),
                new StudyGroupComment(1L, studyGroupPost, user2, studyGroupPost.getCreated_at(), "content2")
        ));
        given(studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupLike(10L, user1, studyGroupPost),
                new StudyGroupLike(11L, user2, studyGroupPost)
        ));
        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupParticipation(1000L, user1, studyGroupPost),
                new StudyGroupParticipation(1001L, user2, studyGroupPost)
        ));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/studygroups")
                .param("limit", String.valueOf(12));

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testLectureStudyGroupDetail_iscreatedAfter() throws Exception {
        studyGroupPost.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        studyGroupPost_alone.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<StudyGroupPost> listOfStudyGroupPost = new ArrayList<>();
        listOfStudyGroupPost.add(studyGroupPost);
        listOfStudyGroupPost.add(studyGroupPost_alone);

        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.ofNullable(lecture));
        given(studyGroupRepository.findAllByLectureId(lecture)).willReturn(listOfStudyGroupPost);
        given(studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupComment(0L, studyGroupPost, user1, studyGroupPost.getCreated_at(), "content"),
                new StudyGroupComment(1L, studyGroupPost, user2, studyGroupPost.getCreated_at(), "content2")
        ));
        given(studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupLike(10L, user1, studyGroupPost),
                new StudyGroupLike(11L, user2, studyGroupPost)
        ));
        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupParticipation(1000L, user1, studyGroupPost),
                new StudyGroupParticipation(1001L, user2, studyGroupPost)
        ));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/studygroups")
                .param("limit", String.valueOf(12));

        mockMvc.perform(request)
                .andDo(print());
    }

    @DisplayName("ReadLecturePostDetail_nolimit")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testLectureStudyGroupDetail_lowcapacity() throws Exception {
        studyGroupPost.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        studyGroupPost_alone.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        List<StudyGroupPost> listOfStudyGroupPost = new ArrayList<>();
        listOfStudyGroupPost.add(studyGroupPost);
        listOfStudyGroupPost.add(studyGroupPost_alone);

        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.ofNullable(lecture));
        given(studyGroupRepository.findAllByLectureId(lecture)).willReturn(listOfStudyGroupPost);
        given(studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost_alone)).willReturn(List.of(
                new StudyGroupComment(0L, studyGroupPost_alone, user1, studyGroupPost_alone.getCreated_at(), "content"),
                new StudyGroupComment(1L, studyGroupPost_alone, user2, studyGroupPost_alone.getCreated_at(), "content2")
        ));
        given(studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost_alone)).willReturn(List.of(
                new StudyGroupLike(10L, user1, studyGroupPost_alone),
                new StudyGroupLike(11L, user2, studyGroupPost_alone)
        ));
        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost_alone)).willReturn(List.of(
                new StudyGroupParticipation(1000L, user1, studyGroupPost_alone),
                new StudyGroupParticipation(1001L, user2, studyGroupPost_alone)
        ));


        RequestBuilder request = MockMvcRequestBuilders.get("/lecture/101/studygroups")
                .param("limit", String.valueOf(12));

        mockMvc.perform(request)
                .andDo(print());
    }

}