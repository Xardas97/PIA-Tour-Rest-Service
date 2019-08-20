package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.endava.mmarko.pia.controllers.ControllerTestUtil.JSON_CONTENT_TYPE;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
public class LoginControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void loginWrongPasswordTest() throws Exception {
        String username = "username";
        String password = "password";

        Map<String, String> loginWrapper = new HashMap<>();
        loginWrapper.put("username", username);
        loginWrapper.put("password", password);

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(loginWrapper);

        when(userService.findByUsernameAndPassword(username, password)).thenThrow(new WrongPasswordError());

        mockMvc.perform(post("/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(2)));
    }


    @Test
    public void loginUserNotFoundTest() throws Exception {
        String username = "username";
        String password = "password";

        Map<String, String> loginWrapper = new HashMap<>();
        loginWrapper.put("username", username);
        loginWrapper.put("password", password);

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(loginWrapper);

        when(userService.findByUsernameAndPassword(username, password)).thenThrow(new UserNotFoundError());

        mockMvc.perform(post("/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(1)));
    }

    @Test
    public void loginSuccessfulTest() throws Exception {
        String username = "username";
        String password = "password";

        Map<String, String> loginWrapper = new HashMap<>();
        loginWrapper.put("username", username);
        loginWrapper.put("password", password);

        User user = new User(username, password, (short) 10, "firstName", "lastName");

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(loginWrapper);

        when(userService.findByUsernameAndPassword(username, password)).thenReturn(user);

        mockMvc.perform(post("/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.password", is(password)))
                .andExpect(jsonPath("$.type", is(10)))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    @Before
    public void init() {
        reset(userService);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   }
}
