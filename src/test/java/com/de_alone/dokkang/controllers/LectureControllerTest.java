package com.de_alone.dokkang.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import com.de_alone.dokkang.repository.LectureRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.Lecture;

import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class LectureControllerTest {

    private List<Lecture> lectures = List.of(
        new Lecture(0L, "SWE-0001", "Software1", "김민수"),
        new Lecture(1L, "SWE-0002", "Software2", "박민정")
    );

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LectureRepository lectureRepository;

    @DisplayName("Get all Lectures Test")
    @Test
    @WithMockUser(username="username", password="password")
    public void getLecture() throws Exception {
        given(lectureRepository.findAll()).willReturn(lectures);

        RequestBuilder request = MockMvcRequestBuilders.get("/lectures");

        mockMvc.perform(request);
    }
}
