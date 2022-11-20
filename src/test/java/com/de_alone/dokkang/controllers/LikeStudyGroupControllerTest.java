package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.StudyGroupLike;
import com.de_alone.dokkang.models.StudyGroupPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.LikeParticipateStudyGroupRequest;
import com.de_alone.dokkang.repository.StudyGroupLikeRepository;
import com.de_alone.dokkang.repository.StudyGroupRepository;
import com.de_alone.dokkang.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class LikeStudyGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StudyGroupRepository studyGroupRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    StudyGroupLikeRepository studyGroupLikeRepository;
    Long testLectureId = 10L;

    User user = new User(10L, "testtestid", "test@test", "testpasswd!@");

    Lecture lecture = new Lecture(testLectureId, "SWE3033", "Introduction to Software", "Cha");

    StudyGroupPost studyGroupPost = new StudyGroupPost(
            101L,
            lecture,
            user,
            "title",
            "content",
            "studytime",
            "studyplace",
            12
    );
    LikeParticipateStudyGroupRequest likeParticipateStudyGroupRequest
            = new LikeParticipateStudyGroupRequest
            (
                    studyGroupPost.getId(),
                    user.getId()
            );

    @DisplayName("testRegisterLikeStudyGroup_409")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterLikeStudyGroup_conflict() throws Exception
    {
        given(studyGroupRepository.findById(studyGroupPost.getId())).willReturn(Optional.ofNullable(studyGroupPost));
        given(userRepository.findById(user.getId())).willReturn(Optional.ofNullable(user));

        given(studyGroupLikeRepository.findByStudyGroupIdAndUserId(studyGroupPost,user)).willReturn(
                List.of(new StudyGroupLike(70L, user, studyGroupPost))
        );
        Map<String, Object> input = new HashMap<>();
        input.put("studygroup_id", likeParticipateStudyGroupRequest.getStudygroup_id());
        input.put("user_id", likeParticipateStudyGroupRequest.getUser_id());

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup-like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is(409))
                .andDo(print());
    }

    @DisplayName("testRegisterLikeStudyGroup_201")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterLikeStudyGroup_success() throws Exception
    {
        given(studyGroupRepository.findById(studyGroupPost.getId())).willReturn(Optional.ofNullable(studyGroupPost));
        given(userRepository.findById(user.getId())).willReturn(Optional.ofNullable(user));

        given(studyGroupLikeRepository.findByStudyGroupIdAndUserId(studyGroupPost,user)).willReturn(
                List.of());

        Map<String, Object> input = new HashMap<>();
        input.put("studygroup_id", likeParticipateStudyGroupRequest.getStudygroup_id());
        input.put("user_id", likeParticipateStudyGroupRequest.getUser_id());


        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup-like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is(201))
                .andDo(print());

    }
}
