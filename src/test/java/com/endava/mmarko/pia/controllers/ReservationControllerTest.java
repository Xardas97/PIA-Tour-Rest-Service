package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.ReservationService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.List;

import static com.endava.mmarko.pia.controllers.ControllerTestUtil.JSON_CONTENT_TYPE;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
public class ReservationControllerTest {
    private static final int USER_ID = 5;
    private static final int RES_ID = 6;

    private MockMvc mockMvc;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}/reservations/{resId}", USER_ID, RES_ID))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).delete(USER_ID, RES_ID);
    }

    @Test
    public void updateTest() throws Exception {
        Reservation res = new Reservation();
        res.setClient(new User());

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(res);

        when(reservationService.save(any())).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/reservations/{resId}", USER_ID, RES_ID)
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", Is.is(RES_ID)));
                //.andExpect(jsonPath("$.client.id", Is.is(USER_ID)));
    }

    @Test
    public void saveTest() throws Exception {
        Reservation res = new Reservation();
        res.setClient(new User());

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(res);

        res.setId(RES_ID);
        when(reservationService.save(any())).thenReturn(res);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/reservations", USER_ID)
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", Is.is(RES_ID)));
                //.andExpect(jsonPath("$.client.id", Is.is("client")));
    }

    @Test
    public void saveConflictTest() throws Exception {
        Reservation res = new Reservation();
        res.setClient(new User());

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(res);

        when(reservationService.save(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{username}/reservations", "username" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", Is.is(4)));
    }

    @Test
    public void reservationTest() throws Exception {
        Reservation res = new Reservation();
        res.setClient(new User());

        when(reservationService.find(USER_ID, RES_ID)).thenReturn(res);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/reservations/{resId}", USER_ID, RES_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE));
                //.andExpect(jsonPath("$.client", Is.is(USER_ID)));
    }

    @Test
    public void reservationNotFoundTest() throws Exception {
        when(reservationService.find(USER_ID, RES_ID)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/reservations/{resId}", USER_ID, RES_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE));
                //.andExpect(jsonPath("$.code", Is.is(3)));
    }

    @Test
    public void reservationsTest() throws Exception {
        List<Reservation> reservations = new LinkedList<>();
        Reservation res1 = new Reservation(); res1.setClient(new User());
        Reservation res2 = new Reservation(); res2.setClient(new User());
        reservations.add(res1); reservations.add(res2);

        when(reservationService.findByClient(USER_ID)).thenReturn(reservations);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/reservations", USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
                //.andExpect(jsonPath("$[0].client", Is.is(USER_ID)))
                //.andExpect(jsonPath("$[1].client", Is.is(USER_ID + 1)));
    }

    @Before
    public void init() {
        reset(reservationService);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   }
}
