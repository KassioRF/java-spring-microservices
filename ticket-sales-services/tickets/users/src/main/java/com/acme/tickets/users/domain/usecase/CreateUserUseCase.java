package com.acme.tickets.users.domain.usecase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.port.CCNetworkRepositoryPort;
import com.acme.tickets.users.domain.port.UserRepositoryPort;
import com.acme.tickets.users.enums.EnumUserRole;
import com.acme.tickets.users.exception.UseCaseException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final CCNetworkRepositoryPort ccnRepositoryPort;

    public UserDomain validate(UserDomain domain) {

        validateCCNetworkId(domain);
        validateCreditCardNumber(domain);

        validateName(domain);
        validateEmail(domain);
        validatePassword(domain);
        validateCity(domain);
        validateUserRole(domain);

        return domain;

    }

    // Validations
    private void validateCCNetworkId(UserDomain domain) {
        if (domain.getCcNetwork() == null) {
            throw new UseCaseException("Invalid Credit Card Network.");
        }

        UUID ccnId = domain.getCcNetwork().getId();

        if (ccnId == null) {
            throw new UseCaseException("Credit Card Network ID is null.");
        }

        Optional<CCNetworkDomain> ccNetworkDomain = ccnRepositoryPort
                .findById(ccnId);

        if (ccNetworkDomain.isEmpty()) {
            throw new UseCaseException(
                    "Credit Card Network ID does not exist.");
        }

        domain.setCcNetwork(ccNetworkDomain.get());

    }

    private void validateCreditCardNumber(UserDomain domain) {
        // @TODO: Implement the logic for credit card number validation
        if (domain.getCreditCardNumber() == null) {
            throw new UseCaseException("Credit Card Number is null.");
        }
    }

    private void validateName(UserDomain domain) {
        if (domain.getName() == null) {
            throw new UseCaseException("Name is null.");
        }
    }

    private void validateEmail(UserDomain domain) {
        if (domain.getEmail() == null) {
            throw new UseCaseException("E-mail is null.");
        }

        // If email already exists
        if (userRepositoryPort.existsByEmail(domain.getEmail())) {
            throw new UseCaseException("E-mail already exists.");
        }
    }

    private void validatePassword(UserDomain domain) {
        if (domain.getPassword() == null) {
            throw new UseCaseException("Password is null.");
        }
    }

    private void validateCity(UserDomain domain) {

        // @TODO: Implement city validation logic
        if (domain.getCity() == null) {
            throw new UseCaseException("City is null.");
        }
    }

    private void validateUserRole(UserDomain domain) {
        if (Objects.isNull(domain.getRole())) {
            domain.setRole(EnumUserRole.CUSTOMER);
        }
    }

}
