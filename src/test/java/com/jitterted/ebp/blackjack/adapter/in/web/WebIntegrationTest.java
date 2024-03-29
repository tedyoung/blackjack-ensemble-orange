package com.jitterted.ebp.blackjack.adapter.in.web;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Tag("integration")
@Import(IntegrationTestConfiguration.class)
class WebIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getOfHomePageIsStatus200Ok() throws Exception {
        mockMvc.perform(get("/index.html"))
               .andExpect(status().isOk());
    }

    @Test
    void postToStartGameEndpointIsRedirect() throws Exception {
        mockMvc.perform(post("/start-game"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    void getGameEndpointIsStatus200Ok() throws Exception {
        mockMvc.perform(post("/start-game"));
        mockMvc.perform(get("/game/0"))
               .andExpect(status().isOk());
    }

    @Test
    void postToHitEndpointIsRedirect() throws Exception {
        mockMvc.perform(post("/hit/0"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    void getDoneEndpointIs200Ok() throws Exception {
        mockMvc.perform(get("/done/0"))
               .andExpect(status().isNotFound());
    }

    @Test
    void postToStandEndpointIsRedirect() throws Exception {
        mockMvc.perform(post("/stand/0"))
               .andExpect(status().is3xxRedirection());
    }

}
