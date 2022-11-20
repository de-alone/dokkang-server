package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.CreatePostRequest;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardPostRepository boardPostRepository;

    @MockBean
    LectureRepository lectureRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    BoardLikeRepository boardLikeRepository;

    @MockBean
    BoardCommentRepository boardCommentRepository;

    Lecture lecture = new Lecture(101L, "SWE3033", "Intro SE", "Cha");
    User user = new User(1L, "user", "email", "username");
    User user2 = new User(2L, "username", "email", "username");

    CreatePostRequest createPostRequest = new CreatePostRequest(
            lecture.getId(),
            user.getId(),
            "title",
            "content"
    );
    BoardPost boardPost = new BoardPost(
            1000L,
            lecture,
            user,
            createPostRequest.getTitle(),
            createPostRequest.getContent()
    );

    @DisplayName("RegisterPost")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterPost() throws Exception {


        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.of(lecture));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        given(boardPostRepository.save(any(BoardPost.class))).willReturn(boardPost);

        HashMap<String, Object> input = new HashMap<>();
        input.put("user_id", createPostRequest.getUser_id());
        input.put("lecture_id", createPostRequest.getLecture_id());
        input.put("title", createPostRequest.getTitle());
        input.put("content", createPostRequest.getContent());

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = MockMvcRequestBuilders.post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @DisplayName("readPostDetail")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testReadPostDetail() throws Exception {

        given(boardPostRepository.findById(boardPost.getId())).willReturn(Optional.of(boardPost));
        given(boardLikeRepository.findAllByPostId(boardPost)).willReturn(List.of(
                new BoardLike(1000L, user, boardPost),
                new BoardLike(2000L, user2, boardPost)));
        given(boardCommentRepository.findAllByPostId(boardPost)).willReturn(List.of(
                new BoardComment(5L, user, boardPost, Timestamp.valueOf(LocalDateTime.now()), "d"),
                new BoardComment(6L, user2, boardPost, Timestamp.valueOf(LocalDateTime.now()), "e")
        ));


        RequestBuilder request = MockMvcRequestBuilders.get("/post/{id}", boardPost.getId());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
