package com.de_alone.dokkang.controllers;

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

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import com.de_alone.dokkang.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.de_alone.dokkang.DokkangServerApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = DokkangServerApplication.class)
class AuthControllerTest {

    private String username = "helloworld";
    private String password = "newbee...";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @DisplayName("Login Test")
    @Test
    public void testAuthentication() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder request = MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mockMvc.perform(request);
    }
}
