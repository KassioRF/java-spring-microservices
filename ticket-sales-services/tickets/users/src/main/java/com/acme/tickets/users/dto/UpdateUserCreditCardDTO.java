package com.acme.tickets.users.dto;

import java.util.UUID;

public record UpdateUserCreditCardDTO(UUID userId, UUID ccNetworkId,
        String creditCardNumber) {

}
