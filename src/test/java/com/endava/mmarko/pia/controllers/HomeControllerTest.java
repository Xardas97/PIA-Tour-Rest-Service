package com.endava.mmarko.pia.controllers;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class HomeControllerTest {
    @Test
    public void homeTest() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get(("/"))).andExpect(view().name("home"));
    }
}
