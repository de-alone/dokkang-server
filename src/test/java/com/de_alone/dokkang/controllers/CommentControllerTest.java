package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.BoardComment;
import com.de_alone.dokkang.models.BoardPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.repository.BoardCommentRepository;
import com.de_alone.dokkang.repository.BoardPostRepository;
import com.de_alone.dokkang.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
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

import com.de_alone.dokkang.repository.LectureRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.Lecture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BoardPostRepository boardPostRepository;

    @MockBean
    BoardCommentRepository boardCommentRepository;

    @MockBean
    UserRepository userRepository;

    @DisplayName("RegisterComment Test")
    @Test
    public void testRegisterComment() throws Exception {
        Long post_id = 100L;
        user.setId(10L);
        Long user_id = user.getId();
        Long comment_id = 10000L;

        Date created_at = Timestamp.valueOf(LocalDateTime.now());
        String content = "Test of Test";

        BoardComment boardComment = new BoardComment(boardPost, user, created_at, content);
        given(boardPostRepository.findById(post_id)).willReturn(Optional.of(boardPost));
        given(userRepository.findById(user_id)).willReturn(Optional.of(user));
        when(boardCommentRepository.save(any(BoardComment.class))).thenReturn(boardComment);

        Map<String, Object> input = new HashMap<>();
        input.put("post_id", post_id);
        input.put("user_id", user_id);
        input.put("content", content);

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = MockMvcRequestBuilders.post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        //boardComment Id returns nulll... as it is not set.
        mockMvc.perform(request)
                .andDo(print());
    }
}
