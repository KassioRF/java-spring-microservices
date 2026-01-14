package com.acme.tickets.users.dto;

import java.util.UUID;

public record DeleteUserDTO(UUID id, String password) {

}
