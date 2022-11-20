package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.StudyGroupParticipation;
import com.de_alone.dokkang.models.StudyGroupPost;
import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.payload.request.LikeParticipateStudyGroupRequest;
import com.de_alone.dokkang.repository.StudyGroupParticipationRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class ParticipateStudyGroupControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudyGroupParticipationRepository studyGroupParticipationRepository;

    @MockBean
    StudyGroupRepository studyGroupRepository;

    @MockBean
    UserRepository userRepository;

    Lecture lecture = new Lecture(101L, "SWE3033", "IE to SE", "Cha");
    User user = new User(1L, "user", "email", "password");
    User user2 = new User(2L, "username", "email", "password");
    StudyGroupPost studyGroupPost = new StudyGroupPost(
            1000L,
            lecture,
            user,
            "title",
            "content",
            "studytime",
            "studyplace",
            15
    );
    StudyGroupPost studyGroupPost_alone = new StudyGroupPost(
            1000L,
            lecture,
            user,
            "title",
            "content",
            "studytime",
            "studyplace",
            1
    );

    LikeParticipateStudyGroupRequest likeParticipateStudyGroupRequest = new LikeParticipateStudyGroupRequest(
            studyGroupPost.getId(),
            user2.getId()
    );

    StudyGroupParticipation user1_participation =
            new StudyGroupParticipation(15L, studyGroupPost, user);
    StudyGroupParticipation user2_participation =
            new StudyGroupParticipation(16L, studyGroupPost, user2);

    @DisplayName("testRegisterParticipateStudyGroup")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterParticipateStudyGroup_willCONFLICT() throws Exception
    {
        List<StudyGroupParticipation> participation_info = new ArrayList<>();
        participation_info.add(user2_participation);
        given(studyGroupRepository.findById(studyGroupPost.getId())).willReturn(Optional.of(studyGroupPost));
        given(userRepository.findById(user2.getId())).willReturn(Optional.of(user2));

        given(studyGroupParticipationRepository.findByStudyGroupIdAndUserId(studyGroupPost, user2))
                .willReturn(participation_info);

        Map<String, Object> input = new HashMap<>();
        input.put("studygroup_id", likeParticipateStudyGroupRequest.getStudygroup_id());
        input.put("user_id", likeParticipateStudyGroupRequest.getUser_id());

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup-participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is(409))
                .andDo(print());


    }

    @DisplayName("testRegisterParticipateStudyGroup")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterParticipateStudyGroup_CapacityError() throws Exception
    {
        List<StudyGroupParticipation> participation_info = new ArrayList<>();
        participation_info.add(user1_participation);
        Long studygroup_id = studyGroupPost_alone.getId();
        Long user_id = likeParticipateStudyGroupRequest.getUser_id();

        given(studyGroupRepository.findById(studygroup_id)).willReturn(Optional.of(studyGroupPost_alone));
        given(userRepository.findById(user_id)).willReturn(Optional.of(user2));

        given(studyGroupParticipationRepository.findByStudyGroupIdAndUserId(studyGroupPost, user2))
                .willReturn(participation_info);

        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost))
                .willReturn(participation_info);

        Map<String, Object> input = new HashMap<>();
        input.put("studygroup_id", likeParticipateStudyGroupRequest.getStudygroup_id());
        input.put("user_id", likeParticipateStudyGroupRequest.getUser_id());

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup-participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is(423))
                .andDo(print());

    }

    @DisplayName("testRegisterParticipateStudyGroup")
    @Test
    @WithMockUser(username = "username", password = "password")
    public void testRegisterParticipateStudyGroup_Success2xx() throws Exception
    {
        List<StudyGroupParticipation> participation_info = new ArrayList<>();
        participation_info.add(user1_participation);
        Long studygroup_id = likeParticipateStudyGroupRequest.getStudygroup_id();
        Long user_id = likeParticipateStudyGroupRequest.getUser_id();

        given(studyGroupRepository.findById(studygroup_id)).willReturn(Optional.of(studyGroupPost));
        given(userRepository.findById(user_id)).willReturn(Optional.of(user));

        given(studyGroupParticipationRepository.findByStudyGroupIdAndUserId(studyGroupPost, user2))
                .willReturn(participation_info);

        given(studyGroupParticipationRepository.findAllByStudyGroupId(studyGroupPost))
                .willReturn(participation_info);

        Map<String, Object> input = new HashMap<>();
        input.put("studygroup_id", likeParticipateStudyGroupRequest.getStudygroup_id());
        input.put("user_id", likeParticipateStudyGroupRequest.getUser_id());

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup-participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());


    }


}
