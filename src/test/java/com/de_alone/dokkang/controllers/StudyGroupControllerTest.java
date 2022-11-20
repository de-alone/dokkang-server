package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.payload.request.CreateStudyGroupRequest;
import com.de_alone.dokkang.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
public class StudyGroupControllerTest {
    Lecture lecture = new Lecture(1L, "SWE3033", "Introduction to SE", "Cha");
    User user = new User(1L, "user", "email@email.com", "password");
    User user2 = new User(2L, "username", "email", "password");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    LectureRepository lectureRepository;

    @MockBean
    UserLectureRepository userLectureRepository;
    @MockBean
    StudyGroupRepository studyGroupRepository;

    @MockBean
    StudyGroupLikeRepository studyGroupLikeRepository;

    @MockBean
    StudyGroupCommentRepository studyGroupCommentRepository;

    @MockBean
    StudyGroupParticipationRepository studyGroupParticipationRepository;

    CreateStudyGroupRequest createStudyGroupRequest
            = new CreateStudyGroupRequest(
            lecture.getId(),
            user.getId(),
            "studygrouptitle",
            "studygroupcontent",
            10,
            "studygrouptime",
            "studygroupplace"
    );

    StudyGroupPost studyGroupPost = new StudyGroupPost(
            101L,
            lecture,
            user,
            createStudyGroupRequest.getTitle(),
            createStudyGroupRequest.getContent(),
            createStudyGroupRequest.getStudytime(),
            createStudyGroupRequest.getStudyplace(),
            createStudyGroupRequest.getStudycapacity()
    );

    @DisplayName("registerstudygroup")
    @Test
    @WithMockUser(username="username", password="password")
    public void testRegisterStudyGroup() throws Exception {

        given(lectureRepository.findById(lecture.getId())).willReturn(Optional.of(lecture));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        HashMap<String, Object> input = new HashMap<>();
        input.put("lecture_id", createStudyGroupRequest.getLecture_id());
        input.put("user_id", createStudyGroupRequest.getUser_id());
        input.put("title", createStudyGroupRequest.getTitle());
        input.put("content", createStudyGroupRequest.getContent());
        input.put("studytime", createStudyGroupRequest.getStudytime());
        input.put("studyplace", createStudyGroupRequest.getStudyplace());
        input.put("studycapacity", createStudyGroupRequest.getStudycapacity());


        given(studyGroupRepository.save(any(StudyGroupPost.class))).willReturn(studyGroupPost);

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
    @DisplayName("readStudyGroupDetail")
    @Test
    @WithMockUser(username="username", password="password")
    public void testReadStudyGroupDetail() throws Exception {
        Long studyGroupId = studyGroupPost.getId();
        given(studyGroupRepository.findById(studyGroupPost.getId())).willReturn(Optional.of(studyGroupPost));
        given(studyGroupLikeRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupLike(1000L, user, studyGroupPost),
                new StudyGroupLike(2000L, user2, studyGroupPost)));
        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupParticipation(10000L, user, studyGroupPost),
                new StudyGroupParticipation(10001L, user2, studyGroupPost)
        ));
        given(studyGroupCommentRepository.findAllByStudyGroupId(studyGroupPost)).willReturn(List.of(
                new StudyGroupComment(5L, user, studyGroupPost, Timestamp.valueOf(LocalDateTime.now()), "d"),
                new StudyGroupComment(6L, user, studyGroupPost, Timestamp.valueOf(LocalDateTime.now()), "e")
                ));


        RequestBuilder request = MockMvcRequestBuilders.get("/studygroup/{id}", studyGroupId);

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
}
