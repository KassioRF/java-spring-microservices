package com.acme.tickets.users.dto;

import java.util.UUID;

public record CreateUserDTO(
        String name,
        String email,
        String password,
        String creditCardNumber,
        UUID ccNetworkId,
        String city) {
}
