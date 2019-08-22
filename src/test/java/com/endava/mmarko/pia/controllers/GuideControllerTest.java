package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.config.TestConfig;
import com.endava.mmarko.pia.config.WebConfig;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.GuideService;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
public class GuideControllerTest {
    private static final int ID = 5;
    
    private MockMvc mockMvc;
    @Autowired
    private GuideService guideService;
    @Autowired
    private WebApplicationContext context;

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
