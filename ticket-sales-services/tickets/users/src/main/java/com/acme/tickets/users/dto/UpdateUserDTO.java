package com.acme.tickets.users.dto;

import java.util.UUID;

public record UpdateUserDTO(UUID id, String name, String city) {

}
