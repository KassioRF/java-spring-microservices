package com.acme.tickets.sales.business.service.clients;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.sales.business.service.clients.dtos.PaymentDTO;

/**
 * Just to simulate an external payment service
 */
@Component
public class PaymentServiceClient {

    public boolean processPayment(PaymentDTO payloadDTO) {
        // Just a simulation
        return true;
    }

    public boolean requestRefund(UUID saleId) {
        // Just a simulation
        return true;
    }
}
