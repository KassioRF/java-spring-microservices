package com.acme.tickets.users.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.acme.tickets.users.dto.CreateUserDTO;
import com.acme.tickets.users.dto.DeleteUserDTO;
import com.acme.tickets.users.dto.UpdateUserCreditCardDTO;
import com.acme.tickets.users.dto.UpdateUserDTO;
import com.acme.tickets.users.dto.UserDTO;
import com.acme.tickets.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    // ---------- tests ----------

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.create(any()))
                .thenReturn(validUserDTO());

        performPost("/users", validCreateUserDTO())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateProfile() throws Exception {
        when(userService.updateProfile(any()))
                .thenReturn(validUserDTO());

        performPut("/users/profile",
                new UpdateUserDTO(UUID.randomUUID(), "John", "SP"))
                        .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCreditCard() throws Exception {
        when(userService.updateCreditCard(any()))
                .thenReturn(validUserDTO());

        performPut("/users/credit-card",
                new UpdateUserCreditCardDTO(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "4111111111111111"))
                                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        performDelete("/users",
                new DeleteUserDTO(
                        UUID.randomUUID(),
                        "123456"))
                                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.getById(id))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/users/id/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        when(userService.getAll())
                .thenReturn(List.of());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetUsersByName() throws Exception {
        when(userService.getByName("john"))
                .thenReturn(List.of());

        mockMvc.perform(get("/users/name/{name}", "john"))
                .andExpect(status().isOk());
    }

    // ---------- helpers ----------

    private UserDTO validUserDTO() {
        return new UserDTO(
                UUID.randomUUID(),
                "John",
                "john@doe.com",
                "São Paulo");
    }

    private CreateUserDTO validCreateUserDTO() {
        return new CreateUserDTO(
                "John",
                "john@doe.com",
                "123456",
                "4111111111111111",
                UUID.randomUUID(),
                "São Paulo");
    }

    private String toJson(Object body) throws Exception {
        return objectMapper.writeValueAsString(body);
    }

    private ResultActions performPost(String path, Object body) throws Exception {
        return mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(body)));
    }

    private ResultActions performPut(String path, Object body) throws Exception {
        return mockMvc.perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(body)));
    }

    private ResultActions performDelete(String path, Object body) throws Exception {
        return mockMvc.perform(delete(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(body)));
    }

}
