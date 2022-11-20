package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.repository.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
    Long lecture_id = 101L;
    Lecture lecture = new Lecture(lecture_id, "SWE3033", "Software Engineering", "Cha");

    User user1 = new User(1L, "username", "email@email.com", "password");
    User user2 = new User(2L, "user", "email@email.com", "password");

    BoardPost boardPost1 = new BoardPost(1L, lecture, user1 , "test of board1", "content");

    BoardPost boardPost2 = new BoardPost(2L, lecture, user2, "test of board2","content");


    BoardComment boardComment1
            = new BoardComment(boardPost1, user1, new Date(), "content" );
    BoardComment boardComment2
            = new BoardComment(boardPost1, user1, new Date(), "content");

    BoardLike boardLike
            = new BoardLike(1L, user2, boardPost1);
    BoardLike boardLike2
            = new BoardLike(2L, user1, boardPost2);
    @DisplayName("get Post Lecture Detail")
    @Test
    @WithMockUser(username="username", password="password")
    public void testreadLecturePostDetail() throws Exception {
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



}