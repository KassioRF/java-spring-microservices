package com.acme.tickets.users.domain.usecase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.port.CCNetworkRepositoryPort;
import com.acme.tickets.users.enums.EnumUserRole;
import com.acme.tickets.users.exception.UseCaseException;
import com.acme.tickets.users.repository.IUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Component
public class CreateUserUseCase {

    @Setter
    private UserDomain domain;
    private final IUserRepository repository;
    private final CCNetworkRepositoryPort ccnRepositoryPort;

    public void validate() {

        validateCCNetworkId();
        validateCreditCardNumber();

        validateName();
        validateEmail();
        validatePassword();
        validateCity();
        validateUserRole();

    }

    // Validations
    private void validateName() {
        if (this.domain.getName() == null) {
            throw new UseCaseException("Name is null.");
        }
    }

    private void validateCCNetworkId() {
        if (this.domain.getCcNetwork() == null) {
            throw new UseCaseException("Invalid Credit Card Network.");
        }

        UUID ccnId = this.domain.getCcNetwork().getId();

        if (ccnId == null) {
            throw new UseCaseException("Credit Card Network ID is null.");
        }

        Optional<CCNetworkDomain> ccNetworkDomain = ccnRepositoryPort.findById(ccnId);

        if (ccNetworkDomain.isEmpty()) {
            throw new UseCaseException("Credit Card Network ID does not exist.");
        }

        this.domain.setCcNetwork(ccNetworkDomain.get());

    }

    private void validateEmail() {
        if (this.domain.getEmail() == null) {
            System.out.println("Email is null: " + this.domain.getEmail());
            throw new UseCaseException("E-mail is null.");
        }

        // If email already exists
        if (this.repository.findByEmail(this.domain.getEmail()).isPresent()) {
            throw new UseCaseException("E-mail already exists.");
        }
    }

    private void validatePassword() {
        if (this.domain.getPassword() == null) {
            throw new UseCaseException("Password is null.");
        }
    }

    private void validateCity() {

        // @TODO: Implement city validation logic
        if (this.domain.getCity() == null) {
            throw new UseCaseException("City is null.");
        }
    }

    private void validateCreditCardNumber() {
        // @TODO: Implement a better CCNumber validation logic
        if (this.domain.getCreditCardNumber() == null) {
            throw new UseCaseException("Credit Card Number is null.");
        }
    }

    private void validateUserRole() {
        if (Objects.isNull(this.domain.getRole())) {
            this.domain.setRole(EnumUserRole.CUSTOMER);
        }
    }

}
