package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.CreateCommentRequest;
import com.de_alone.dokkang.repository.BoardCommentRepository;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
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

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class CommentControllerTest {
    Long testLectureId = 1000L;

    User user = new User("testtestid", "test@test", "testpasswd!@");

    Lecture lecture = new Lecture(testLectureId, "SWE3033", "Introduction to Software", "Cha");

    BoardPost boardPost = new BoardPost(lecture, user, "board title", "board content" );

    CreateCommentRequest createCommentRequest = new CreateCommentRequest(
            boardPost.getId(),
            user.getId(),
            "content"
    );
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BoardPostRepository boardPostRepository;

    @MockBean
    BoardCommentRepository boardCommentRepository;

    @MockBean
    UserRepository userRepository;

    @Captor
    private ArgumentCaptor<BoardComment> repoCaptor;

    @DisplayName("RegisterComment Test")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterComment() throws Exception {
        Long post_id = boardPost.getId();
//        Assertions.assertThat(createCommentRequest.getPost_id()).isEqualTo(boardPost.getId());
        Long user_id = createCommentRequest.getUser_id();
        given(boardPostRepository.findById(post_id)).willReturn(Optional.of(boardPost));
        given(userRepository.findById(user_id)).willReturn(Optional.of(user));

        BoardComment boardComment
                = new BoardComment(1L, boardPost, user, Timestamp.valueOf(LocalDateTime.now()), "content");

        boardCommentRepository.save(boardComment);
        given(boardCommentRepository.save(any(BoardComment.class))).willReturn(boardComment);
        verify(boardCommentRepository).save(repoCaptor.capture());
        final BoardComment actual = repoCaptor.getValue();
        assertThat(boardComment.getId()).isEqualTo(actual.getId());


        Map<String, Object> input = new HashMap<>();
        input.put("post_id", createCommentRequest.getPost_id());
        input.put("user_id", createCommentRequest.getUser_id());
        input.put("content", createCommentRequest.getContent());

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
}
