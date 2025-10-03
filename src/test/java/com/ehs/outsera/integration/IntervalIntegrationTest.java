package com.ehs.outsera.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntervalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void intervalsShouldContainExpectedProducerEntries() throws Exception {
        mockMvc.perform(get("/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min", notNullValue()))
                .andExpect(jsonPath("$.max", notNullValue()))
                .andExpect(jsonPath("$.min[*].producer", hasItem("Joel Silver")))
                .andExpect(jsonPath("$.min[?(@.producer=='Joel Silver')].interval", hasItem(1)))
                .andExpect(jsonPath("$.max[*].producer", hasItem("Matthew Vaughn")))
                .andExpect(jsonPath("$.max[?(@.producer=='Matthew Vaughn')].interval", hasItem(13)));
    }

}
