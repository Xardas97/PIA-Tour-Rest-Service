package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.services.DepartureService;
import com.endava.mmarko.pia.services.GuideService;
import com.endava.mmarko.pia.services.TourService;
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

import java.util.Arrays;
import java.util.Date;
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
public class GuideControllerTest {
    private static final int ID = 5;
    
    private MockMvc mockMvc;
    @Autowired
    private GuideService guideService;
    @Autowired
    private DepartureService departureService;
    @Autowired
    private TourService tourService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void departuresByGuideTest() throws Exception {
        List<Departure> departures = new LinkedList<>();
        Date date = new Date();
        Departure dep1 = new Departure(); dep1.setDate(date);
        Departure dep2 = new Departure(); dep2.setDate(date);
        departures.add(dep1);  departures.add(dep2);

        when(departureService.findByGuide(ID)).thenReturn(departures);
        mockMvc.perform(get("/guides/{ID}/departures", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].date", is(date.getTime())))
                .andExpect(jsonPath("$[1].date", is(date.getTime())));
    }

    @Test
    public void toursByGuideTest() throws Exception {

        List<Tour> tours = Arrays.asList(
                new Tour("name1", "description1", "meeting point1", 1),
                new Tour("name2", "description2", "meeting point2", 2));

        when(tourService.findByGuide(ID)).thenReturn(tours);
        mockMvc.perform(get("/guides/{ID}/tours", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].description", is("description2")))
                .andExpect(jsonPath("$[0].meetingPoint", is("meeting point1")))
                .andExpect(jsonPath("$[1].meetingPoint", is("meeting point2")))
                .andExpect(jsonPath("$[0].minPeople", is(1)))
                .andExpect(jsonPath("$[1].minPeople", is(2)));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/guides/{id}", ID ))
                .andExpect(status().isOk());

        verify(guideService, times(1)).delete(ID);
    }

    @Test
    public void updateTest() throws Exception {
        Guide guide = new Guide();

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(guide);

        when(guideService.save(any())).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(put("/guides/{id}", ID )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(ID)));
    }

    @Test
    public void saveTest() throws Exception {
        Guide guide = new Guide();

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(guide);

        guide.setId(ID);
        when(guideService.save(any())).thenReturn(guide);

        mockMvc.perform(post("/guides" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(ID)));
    }

    @Test
    public void saveConflictTest() throws Exception {
        Guide guide = new Guide();

        byte[] unsavedJsonBytes = new ObjectMapper().
                setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(guide);

        when(guideService.save(any())).thenReturn(null);

        mockMvc.perform(post("/guides" )
                .contentType(JSON_CONTENT_TYPE)
                .content(unsavedJsonBytes))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(4)));
    }

    @Test
    public void guideTest() throws Exception {
        Guide guide = new Guide();
        guide.setId(ID);
        when(guideService.find(ID)).thenReturn(guide);

        mockMvc.perform(get("/guides/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(ID)));
    }

    @Test
    public void guideNotFoundTest() throws Exception {
        when(guideService.find(ID)).thenReturn(null);

        mockMvc.perform(get("/guides/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.code", is(3)));
    }

    @Test
    public void guidesTest() throws Exception {
        Guide guide1 = new Guide();
        guide1.setId(ID);
        Guide guide2 = new Guide();
        guide2.setId(ID + 1);
        List<Guide> guides = Arrays.asList(guide1, guide2);

        when(guideService.findAll()).thenReturn(guides);

        mockMvc.perform(get("/guides"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(ID)))
                .andExpect(jsonPath("$[1].id", is(ID + 1)));
    }
    @Before
    public void init(){
        reset(guideService);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
