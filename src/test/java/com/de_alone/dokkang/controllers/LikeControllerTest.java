package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.BoardLike;
import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.repository.BoardLikeRepository;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bytecode.Addition;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.de_alone.dokkang.repository.LectureRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.Lecture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class LikeControllerTest {
    Long testPostId  = 1L;
    Long testLectureId = 10L;
    Long testUserId = 100L;
    Long testUserId_alreadyLiked = 101L;

    User testUser =
            new User(testUserId, "username", "email@email", "password");
    User testUser_alreadyLiked =
            new User(testUserId_alreadyLiked, "username1", "email1@email2", "password2");
    BoardPost boardPost = new BoardPost(
            new Lecture(testLectureId, "SWE3033", "Software Engineering", "Cha"),
            testUser,
            "2022-02-22",
            "I like to write something!"
    );

    List<BoardLike> existingBoardLike = List.of(new BoardLike(boardPost, testUser_alreadyLiked));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;
    @MockBean
    BoardLikeRepository boardLikeRepository;
    @MockBean
    BoardPostRepository boardPostRepository;

    public List<BoardLike> getBoardLikeByUser(Long user_id) {
        List<BoardLike> listOfParsedData = new ArrayList<>();
        for (BoardLike b:existingBoardLike) {
            if (Objects.equals(b.getUserId().getId(), user_id)){
                listOfParsedData.add(b);
            }
        }
        return listOfParsedData;
    }

    @DisplayName("Success Case")
    @Test
    @WithMockUser(username = "username", password="password")
    public void testRegisterLikePost_notLiked_success() throws Exception {
        given(boardPostRepository.findById(testPostId)).willReturn(Optional.of(boardPost));
        given(userRepository.findById(testUserId)).willReturn(Optional.of(testUser));
        //Give board data.

        when(boardLikeRepository.findByPostIdAndUserId(boardPost, testUser))
                .thenReturn(getBoardLikeByUser(testUserId));
        when(boardLikeRepository.save(any(BoardLike.class))).thenReturn(new BoardLike(boardPost,testUser));

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Long> input = new HashMap<>();
        input.put("post_id", testPostId);
        input.put("user_id", testUserId);

        RequestBuilder request = MockMvcRequestBuilders.post("/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request).andDo(print());
    }
    @DisplayName("Already Liked")
    @Test
    @WithMockUser(username = "username", password="password")
    public void testRegisterLikePost_alreadyLiked_409Error() throws Exception
    {

        given(boardPostRepository.findById(testPostId)).willReturn(Optional.of(boardPost));
        given(userRepository.findById(testUserId_alreadyLiked)).willReturn(Optional.of(testUser_alreadyLiked));
        //Give board data.

        when(boardLikeRepository.findByPostIdAndUserId(boardPost, testUser_alreadyLiked))
                .thenReturn(getBoardLikeByUser(testUserId_alreadyLiked));
        when(boardLikeRepository.save(any(BoardLike.class))).thenReturn(new BoardLike(boardPost,testUser_alreadyLiked));
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Long> input = new HashMap<>();
        input.put("post_id", testPostId);
        input.put("user_id", testUserId_alreadyLiked);

        RequestBuilder request = MockMvcRequestBuilders.post("/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request).andDo(print());
    }
}
