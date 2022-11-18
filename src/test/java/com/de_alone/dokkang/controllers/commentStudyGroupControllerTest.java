
package com.de_alone.dokkang.controllers;

import com.de_alone.dokkang.DokkangServerApplication;
import com.de_alone.dokkang.models.*;
import com.de_alone.dokkang.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.de_alone.dokkang.models.User;
import com.de_alone.dokkang.repository.UserRepository;
import com.de_alone.dokkang.models.Lecture;

@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class commentStudyGroupControllerTest {

    Long testLectureId = 10L;
    Long studyGroupId = 100L;

    User user = new User(10L, "testtestid", "test@test", "testpasswd!@");

    String content = "Test Of Registering Comment";
    Lecture lecture = new Lecture(testLectureId, "SWE3033", "Introduction to Software", "Cha");
    Date created_at = Timestamp.valueOf(LocalDateTime.now());

    StudyGroupPost studyGroupPost = new StudyGroupPost(
            studyGroupId,
            lecture,
            user,
            "title of studygrouppost",
            "content of studygrouppost",
            "time of study",
            "place of study",
            10
    );
    StudyGroupComment studyGroupComment;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudyGroupRepository studyGroupRepository;

    @MockBean
    StudyGroupCommentRepository studyGroupCommentRepository;

    @MockBean
    UserRepository userRepository;

    @Captor
    private ArgumentCaptor<StudyGroupComment> repoCaptor;

    @DisplayName("RegisterCommentStudyGroup")
    @Test
    @WithMockUser(username="username", password="password")
    public void testRegisterCommentStudyGroup() throws Exception {
        given(studyGroupRepository.findById(studyGroupId)).willReturn(Optional.of(studyGroupPost));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        StudyGroupComment studyGroupComment = new StudyGroupComment(0L, studyGroupPost, user, created_at, content);

//        when(studyGroupCommentRepository.save(studyGroupComment)).then
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> input = new HashMap<>();
        input.put("studygroup_id", studyGroupId);
        input.put("user_id", user.getId());
        input.put("content", content);

        RequestBuilder request = MockMvcRequestBuilders.post("/studygroup-comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));
//          FIXME!!!
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andDo(print());

        verify(studyGroupCommentRepository).save(repoCaptor.capture());
        final StudyGroupComment actual = repoCaptor.getValue();
        assertThat(studyGroupComment.getStudyGroupId()).isEqualTo(actual.getStudyGroupId());
    }
}

