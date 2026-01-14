package com.acme.tickets.users.domain.usecase;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.port.CCNetworkRepositoryPort;
import com.acme.tickets.users.exception.UseCaseException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateUserCreditCardUseCase {

    private final CCNetworkRepositoryPort ccnRepositoryPort;

    public UserDomain validate(UserDomain domain, UUID ccNetworkId,
            String creditCardNumber) {

        validateCCNetworkId(domain, ccNetworkId);
        validateCreditCardNumber(domain, creditCardNumber);

        return domain;
    }

    private void validateCCNetworkId(UserDomain domain, UUID ccNetworkId) {
        if (ccNetworkId == null) {
            throw new UseCaseException("Invalid Credit Card Network.");
        }

        Optional<CCNetworkDomain> ccNetworkDomain = ccnRepositoryPort
                .findById(ccNetworkId);

        if (ccNetworkDomain.isEmpty()) {
            throw new UseCaseException(
                    "Credit Card Network ID does not exist.");
        }

        domain.setCcNetwork(ccNetworkDomain.get());
    }

    private void validateCreditCardNumber(UserDomain domain,
            String creditCardNumber) {
        // @TODO: Implement the logic for credit card number validation
        if (creditCardNumber == null) {
            throw new UseCaseException("Credit Card Number is null.");
        }

        domain.setCreditCardNumber(creditCardNumber);
    }

}
