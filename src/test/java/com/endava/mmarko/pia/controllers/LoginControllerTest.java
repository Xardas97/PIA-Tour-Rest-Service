package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final byte[] UNSAVED_JSON_BYTES;
    
    static {
        Map<String, String> loginWrapper = new HashMap<>();
        loginWrapper.put("username", USERNAME);
        loginWrapper.put("password", PASSWORD);
        byte[] unsavedJsonBytes;
        try {
            unsavedJsonBytes = new ObjectMapper().
                    setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(loginWrapper);
        } catch (JsonProcessingException e) {
            unsavedJsonBytes = new byte[0];
            e.printStackTrace();
        }
        UNSAVED_JSON_BYTES = unsavedJsonBytes;
    }
    
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void loginWrongPasswordTest() throws Exception {
        when(userService.findByUsernameAndPassword(USERNAME, PASSWORD)).thenThrow(new WrongPasswordError());

        mockMvc.perform(post("/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(UNSAVED_JSON_BYTES))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(2)));
    }


    @Test
    public void loginUserNotFoundTest() throws Exception {
        when(userService.findByUsernameAndPassword(USERNAME, PASSWORD)).thenThrow(new UserNotFoundError());

        mockMvc.perform(post("/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(UNSAVED_JSON_BYTES))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(1)));
    }

    @Test
    public void loginSuccessfulTest() throws Exception {
        User user = new User(USERNAME, PASSWORD, true, "firstName", "lastName");
        when(userService.findByUsernameAndPassword(USERNAME, PASSWORD)).thenReturn(user);

        mockMvc.perform(post("/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(UNSAVED_JSON_BYTES))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.guide", is(true)))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    @Before
    public void init() {
        reset(userService);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   
    }
}
