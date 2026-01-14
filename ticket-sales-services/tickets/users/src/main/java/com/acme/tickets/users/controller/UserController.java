package com.acme.tickets.users.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tickets.users.dto.CreateUserDTO;
import com.acme.tickets.users.dto.DeleteUserDTO;
import com.acme.tickets.users.dto.UpdateUserCreditCardDTO;
import com.acme.tickets.users.dto.UpdateUserDTO;
import com.acme.tickets.users.dto.UserDTO;
import com.acme.tickets.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO payload) {
        UserDTO user = userService.create(payload);
        return ResponseEntity.ok(user);
    }

    @PutMapping("profile")
    public ResponseEntity<UserDTO> updateProfile(
            @RequestBody UpdateUserDTO payload) {
        UserDTO user = userService.updateProfile(payload);
        return ResponseEntity.ok(user);
    }

    @PutMapping("credit-card")
    public ResponseEntity<UserDTO> updateCreditCard(
            @RequestBody UpdateUserCreditCardDTO payload) {
        UserDTO user = userService.updateCreditCard(payload);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteUserDTO payload) {
        userService.delete(payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<UserDTO> getById(
            @PathVariable(value = "userId") UUID id) {

        Optional<UserDTO> userOptional = userService.getById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userOptional.get());
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<UserDTO>> getByName(
            @PathVariable(value = "name") String name) {
        List<UserDTO> users = userService.getByName(name);
        return ResponseEntity.ok(users);
    }

}
