package com.acme.tickets.sales.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tickets.sales.business.service.clients.UserServiceClient;
import com.acme.tickets.sales.business.service.clients.dtos.UserServiceDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("clients/users")
public class UserClientController {

    private final UserServiceClient client;

    @GetMapping
    public ResponseEntity<List<UserServiceDTO>> getAll() {
        return ResponseEntity.ok(client.getAllUsers());
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<UserServiceDTO> getById(@PathVariable UUID userId) {
        return ResponseEntity.ok(client.getById(userId));
    }

}
