package com.example.login_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.login_test.core.security.JwtTokenProvider;
import com.example.login_test.user.UserController;
import com.example.login_test.user.UserRequest;
import com.example.login_test.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testJoin() throws Exception{
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();

        joinDTO.setEmail("admin@green.com");
        joinDTO.setPassword("password@2023");
        joinDTO.setUserName("김그린");

        String requestBody = objectMapper.writeValueAsString(joinDTO);

        ResultActions resultActions = mockMvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(header().exists(JwtTokenProvider.HEADER))
                .andExpect(jsonPath("$.success").value("true")
                );

    }


}
