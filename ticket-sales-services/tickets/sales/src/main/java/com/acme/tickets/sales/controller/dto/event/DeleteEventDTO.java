package com.acme.tickets.sales.controller.dto.event;

import java.util.UUID;

// @TODO use Jwt Auth to allow only ADMINS and ORGANIZERS (That owns) Users to Delete Events
public record DeleteEventDTO(UUID eventId, UUID organizerId) {

}
