package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.models.UserLecture;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import com.de_alone.dokkang.repository.LectureRepository;
import com.de_alone.dokkang.repository.UserLectureRepository;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.Lecture;
import com.de_alone.dokkang.models.User;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class UserControllerTest {

    private String username = "helloworld";
    private String password = "newbee...";
    private String email = "email@email.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    LectureRepository lectureRepository;

    @MockBean
    UserLectureRepository userLectureRepository;

    @DisplayName("SignUp Test")
    @Test
    public void testSignUp() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword(password);
        user.setEmail(email);
        given(userRepository.existsByUsername("username")).willReturn(false);
        given(userRepository.existsByEmail(email)).willReturn(false);

        Map<String, String> input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
        input.put("email", email);

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request);
    }

    @DisplayName("ReadUserDetail")
    @Test
    public void testReadUserDetail() throws Exception{
        Long testId = 0L;
        User user = new User("testUserName", "test@test.com", "testPassword");
        given(userRepository.findById(testId)).willReturn(Optional.of(user));

        RequestBuilder request = MockMvcRequestBuilders.get("/user");

        mockMvc.perform(request);
    }

    @DisplayName("Get Lectures of User Test")
    @Test
    public void testGetLecturesOfUser() throws Exception {
        List<Long> userLectureIDs = List.of(0L, 1L);
        Lecture lecture = new Lecture(0L, "SWE-0001", "Software1", "김민수");

        given(userLectureRepository.findLectureById(0L)).willReturn(userLectureIDs);
        given(lectureRepository.findAllById(userLectureIDs)).willReturn(List.of(lecture));

        RequestBuilder request = MockMvcRequestBuilders.get("/user/0/lectures");

        mockMvc.perform(request);
    }

    @DisplayName("Testing updateUserLecture")
    @Test
    public void testUpdateUserLectures() throws Exception {

        Long testUserId = 0L;
        User user = new User();
        List<Long> testLectureIds = List.of(0L, 1L, 2L, 3L);
        ArrayList<Long> userlectureId = new ArrayList<Long>();
        given(userLectureRepository.deleteLectureById(testUserId))
                .willReturn(0);

        for(Long lectureId: testLectureIds)
        {
            Lecture lecture = new Lecture();
            UserLecture userlecture = new UserLecture();
            given(lectureRepository.findById(lectureId)).willReturn(Optional.of(lecture));
            given(userRepository.findById(testUserId)).willReturn(Optional.of(user));
            userlecture.setLectureId(lectureRepository.findById(lectureId).orElseThrow());
            userlecture.setUserId(userRepository.findById(testUserId).orElseThrow());
            userlectureId.add(userlecture.getId());
        }

        Map<String, List<Long>> input = new HashMap<>();
        input.put("lecture_ids", userlectureId);


        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = MockMvcRequestBuilders.put("/user/0/lectures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request);
    }
}

