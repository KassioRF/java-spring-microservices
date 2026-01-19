package com.acme.tickets.sales.business.converter;

import com.acme.tickets.sales.controller.dto.event.CreateEventDTO;
import com.acme.tickets.sales.controller.dto.event.EventDTO;
import com.acme.tickets.sales.infraestructure.entity.EventEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventConverter {

    public static EventDTO toDTO(EventEntity entity) {
        return EventDTO.builder()
                .id(entity.getId())
                .organizerId(entity.getOrganizerId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .allotedTickets(entity.getAllotedTickets())
                .eventDate(entity.getEventDate())
                .expectedDuration(entity.getExpectedDuration())
                .startingSales(entity.getStartingSales())
                .endingSales(entity.getEndingSales())
                .price(entity.getPrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }

    public static EventEntity toEntity(CreateEventDTO dto) {
        return EventEntity.builder()
                .organizerId(dto.getOrganizerId())
                .name(dto.getName())
                .description(dto.getDescription())
                .type(dto.getType())
                .allotedTickets(dto.getAllotedTickets())
                .eventDate(dto.getEventDate())
                .expectedDuration(dto.getExpectedDuration())
                .startingSales(dto.getStartingSales())
                .endingSales(dto.getEndingSales())
                .price(dto.getPrice())
                .build();
    }

    public static EventEntity toEntity(EventDTO dto) {
        return EventEntity.builder()
                .id(dto.getId())
                .organizerId(dto.getOrganizerId())
                .name(dto.getName())
                .description(dto.getDescription())
                .type(dto.getType())
                .allotedTickets(dto.getAllotedTickets())
                .eventDate(dto.getEventDate())
                .expectedDuration(dto.getExpectedDuration())
                .startingSales(dto.getStartingSales())
                .endingSales(dto.getEndingSales())
                .price(dto.getPrice())
                .build();
    }

}
