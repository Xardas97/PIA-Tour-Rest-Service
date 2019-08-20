package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.services.DepartureService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.*;
import static com.endava.mmarko.pia.controllers.ControllerTestUtil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
public class DepartureControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private DepartureService departureService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void hasEnoughPeopleTest() throws Exception {
        int id = 5;

        when(departureService.hasEnoughPeople(id)).thenReturn(true);
        mockMvc.perform(get("/departures/{id}/has_enough_people", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", is(true)));

        when(departureService.hasEnoughPeople(id)).thenReturn(false);
        mockMvc.perform(get("/departures/{id}/has_enough_people", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", is(false)));

    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/departures/{id}", 10 ))
                .andExpect(status().isOk());

        verify(departureService, times(1)).delete(10);
    }

    @Test
    public void updateTest() throws Exception {
        Date date = new Date();
        Departure dep = new Departure(); dep.setDate(date);

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(dep);

        when(departureService.update(any())).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(put("/departures/{id}", 10 )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.date", is(date.getTime())));
    }

    @Test
    public void saveTest() throws Exception {
        Date date = new Date();
        Departure dep = new Departure(); dep.setDate(date);

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(dep);

        dep.setId(10);
        when(departureService.save(any())).thenReturn(dep);

        mockMvc.perform(post("/departures" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.date", is(date.getTime())));
    }

    @Test
    public void saveConflictTest() throws Exception {
        Date date = new Date();
        Departure dep = new Departure(); dep.setDate(date);

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(dep);

        when(departureService.save(any())).thenReturn(null);

        mockMvc.perform(post("/departures" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(4)));
    }

    @Test
    public void departureTest() throws Exception {
        Date date = new Date();
        Departure dep = new Departure(); dep.setDate(date);
        when(departureService.find(1)).thenReturn(dep);

        mockMvc.perform(get("/departures/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.date", is(date.getTime())));
    }

    @Test
    public void departureNotFoundTest() throws Exception {
        Date date = new Date();
        Departure dep = new Departure(); dep.setDate(date);

        mockMvc.perform(get("/departures/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(3)));
    }

    @Test
    public void departuresTest() throws Exception {
        List<Departure> departures = new LinkedList<>();
        Date date = new Date();
        Departure dep1 = new Departure();
        dep1.setDate(date);
        Departure dep2 = new Departure();
        dep2.setDate(date);
        departures.add(dep1);
        departures.add(dep2);

        when(departureService.findAll()).thenReturn(departures);

        mockMvc.perform(get("/departures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].date", is(date.getTime())))
                .andExpect(jsonPath("$[1].date", is(date.getTime())));
    }

    @Before
    public void init(){
        reset(departureService);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
