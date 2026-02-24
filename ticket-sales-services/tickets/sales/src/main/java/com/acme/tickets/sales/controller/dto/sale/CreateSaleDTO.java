package com.acme.tickets.sales.controller.dto.sale;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSaleDTO {
    private UUID userId;
    private UUID eventId;
}
