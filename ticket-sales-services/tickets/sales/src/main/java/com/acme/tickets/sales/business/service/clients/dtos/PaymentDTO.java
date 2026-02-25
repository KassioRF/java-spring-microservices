package com.acme.tickets.sales.business.service.clients.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private UUID saleId;
    private UUID userId;
    // private String userCreditCardNumber;
    private double amount;
}
