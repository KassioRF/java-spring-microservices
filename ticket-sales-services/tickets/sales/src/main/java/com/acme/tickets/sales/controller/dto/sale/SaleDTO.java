package com.acme.tickets.sales.controller.dto.sale;

import java.time.LocalDateTime;
import java.util.UUID;

import com.acme.tickets.sales.controller.dto.event.EventDTO;
import com.acme.tickets.sales.enums.EnumSaleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDTO {

    private UUID id;
    private UUID userId;

    private EventDTO event;

    private LocalDateTime dateTime;

    private EnumSaleStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
