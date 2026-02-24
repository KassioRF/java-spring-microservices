package com.acme.tickets.sales.controller.dto.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.acme.tickets.sales.enums.EnumEventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventDTO {

    private UUID eventId;
    private UUID organizerId;
    private String name;
    private String description;
    private EnumEventType type;
    private Integer allotedTickets;
    private LocalDateTime eventDate;
    private LocalDateTime expectedDuration;
    private LocalDateTime startingSales;
    private LocalDateTime endingSales;
    private Float price;

}
