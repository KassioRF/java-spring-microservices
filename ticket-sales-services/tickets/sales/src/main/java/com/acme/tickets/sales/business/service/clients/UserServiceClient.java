package com.acme.tickets.sales.business.service.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.acme.tickets.sales.business.service.clients.dtos.UserServiceDTO;

@FeignClient(name = "users-service", url = "http://localhost:3000")
public interface UserServiceClient {

    @GetMapping("/users")
    public List<UserServiceDTO> getAllUsers();

    @GetMapping("/users/id/{userId}")
    public UserServiceDTO getById(@PathVariable UUID userId);

}
