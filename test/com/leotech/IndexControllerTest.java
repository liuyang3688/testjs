package com.leotech;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.leotech.controller.IndexController;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class IndexControllerTest {
    @Test
    public void testHomePage() throws Exception {
        IndexController controller = new IndexController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/index")).andExpect(view().name("home"));
    }
}
