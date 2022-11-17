package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.response.PostLecture;
import com.de_alone.dokkang.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class PostLectureControllerTest {

    MockMvc mockMvc;

    @MockBean
    LectureRepository lectureRepository;

    @MockBean
    BoardPostRepository boardPostRepository;

    @MockBean
    BoardCommentRepository boardCommentRepository;

    @MockBean
    BoardLikeRepository boardLikeRepository;

    @DisplayName("get Post Lecture Detail")
    @Test
    public void testReadPostLectureDetail_yesBefore() throws Exception {
        // Have to be same lecture_id
        Long lecture_id = 101L;
        Lecture lecture = new Lecture();
        lecture.setId(lecture_id);

        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);

        BoardPost boardPost1 = new BoardPost(lecture, user1 , "test", "content");
        BoardPost boardPost2 = new BoardPost(lecture, user2, "test","content");
        boardPost1.setId(1L);
        boardPost2.setId(2L);

        BoardComment boardComment1
                = new BoardComment(boardPost1, user1, Timestamp.valueOf("2022-10-20 10:20:30"), "content" );
        BoardComment boardComment2
                = new BoardComment(boardPost1, user1,Timestamp.valueOf("2022-12-20 12:20:33"), "content");

        List<BoardPost> ListofBoardPost = List.of(boardPost1, boardPost2);

        given(lectureRepository.findById(lecture_id)).willReturn(Optional.of(lecture));
        given(boardPostRepository.findAllByLectureId(lecture)).willReturn(ListofBoardPost);

        Integer limit = 20;
        String before = "2022-12-22 12:34:56";
        RequestBuilder request = MockMvcRequestBuilders.get("/101/posts")
                .param("limit", String.valueOf(limit))
                .param("before", before);

        mockMvc.perform(request);
    }



}