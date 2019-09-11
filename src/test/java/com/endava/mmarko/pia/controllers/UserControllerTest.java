package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.DepartureService;
import com.endava.mmarko.pia.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import java.util.LinkedList;
import java.util.List;

import static com.endava.mmarko.pia.controllers.ControllerTestUtil.JSON_CONTENT_TYPE;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
public class UserControllerTest {
    private static final int ID = 5;

    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartureService departureService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void saveConflictTest() throws Exception {
        User user = new User("username", "password", true, "firstName", "lastName");

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(user);

        when(userService.save(any())).thenReturn(null);

        mockMvc.perform(post("/users" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(4)));
    }

    @Test
    public void saveTest() throws Exception {
        User user = new User("username", "password", true, "firstName", "lastName");

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(user);

        when(userService.save(any())).thenReturn(user);

        mockMvc.perform(post("/users" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.guide", is(true)))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/users/{userId}", ID))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(ID);
    }

    @Test
    public void findTest() throws Exception {
        User user = new User("username", "password", true, "firstName", "lastName");

        when(userService.find(ID)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.guide", is(true)))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    @Test
    public void findNotFoundTest() throws Exception {
        when(userService.find(ID)).thenReturn(null);

        mockMvc.perform(get("/users/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(1)));
    }

    @Test
    public void findAllTest() throws Exception {
        List<User> users = new LinkedList<>();
        User user1 = new User("username", "password", false, "firstName", "lastName");
        User user2 = new User("username2", "password2", false, "firstName2", "lastName2");
        users.add(user1);
        users.add(user2);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("username")))
                .andExpect(jsonPath("$[0].guide", is(false)))
                .andExpect(jsonPath("$[0].firstName", is("firstName")))
                .andExpect(jsonPath("$[0].lastName", is("lastName")))
                .andExpect(jsonPath("$[1].username", is("username2")))
                .andExpect(jsonPath("$[1].guide", is(false)))
                .andExpect(jsonPath("$[1].firstName", is("firstName2")))
                .andExpect(jsonPath("$[1].lastName", is("lastName2")));
    }

    @Before
    public void init() {
        reset(userService, departureService);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
