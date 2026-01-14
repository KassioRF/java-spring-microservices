package com.acme.tickets.users.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.acme.tickets.users.dto.CCNetworkDTO;
import com.acme.tickets.users.dto.CreateCCNetworkDTO;
import com.acme.tickets.users.service.CCNetworkService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CCNetworkController.class)
class CCNetworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CCNetworkService service;

    // ---------- tests ----------

    @Test
    void shouldCreateCCNetwork() throws Exception {
        when(service.create(any()))
                .thenReturn(validCCNetworkDTO());

        performPost("/ccn", new CreateCCNetworkDTO("VISA"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllCCNetworks() throws Exception {
        when(service.getAll())
                .thenReturn(List.of(validCCNetworkDTO()));

        mockMvc.perform(get("/ccn/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindCCNetworkById() throws Exception {
        UUID id = UUID.randomUUID();

        when(service.findById(id))
                .thenReturn(validCCNetworkDTO(id));

        mockMvc.perform(get("/ccn/id/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindCCNetworksByName() throws Exception {
        when(service.findByName("visa"))
                .thenReturn(List.of(validCCNetworkDTO()));

        mockMvc.perform(get("/ccn/name/{name}", "visa"))
                .andExpect(status().isOk());
    }

    // ---------- helpers ----------

    private CCNetworkDTO validCCNetworkDTO() {
        return new CCNetworkDTO(UUID.randomUUID(), "VISA");
    }

    private CCNetworkDTO validCCNetworkDTO(UUID id) {
        return new CCNetworkDTO(id, "VISA");
    }

    private String toJson(Object body) throws Exception {
        return objectMapper.writeValueAsString(body);
    }

    private ResultActions performPost(String path, Object body) throws Exception {
        return mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(body)));
    }
}
