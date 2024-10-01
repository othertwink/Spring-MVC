package org.othertwink.JsonShop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUserSummary() throws Exception {
        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void testGetUserDetails() throws Exception {
        mockMvc.perform(get("/users/1/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").exists())
                .andExpect(jsonPath("$.orders").isArray());
    }

    @Test
    void testGetUserNotFound() throws Exception {
        mockMvc.perform(get("/user/9999/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserDetailsNotFound() throws Exception {
        mockMvc.perform(get("/user/9999/details"))
                .andExpect(status().isNotFound());
    }
}
