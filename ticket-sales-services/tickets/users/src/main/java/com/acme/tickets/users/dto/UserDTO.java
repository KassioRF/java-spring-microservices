package com.acme.tickets.users.dto;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, String city) {
}
