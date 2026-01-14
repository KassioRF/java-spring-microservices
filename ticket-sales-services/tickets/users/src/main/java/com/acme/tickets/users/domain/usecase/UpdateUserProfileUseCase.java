package com.acme.tickets.users.domain.usecase;

import org.springframework.stereotype.Component;

import com.acme.tickets.users.exception.UseCaseException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {

    public void validate(String name, String city) {
        validateName(name);
        validateCity(city);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new UseCaseException("Name is null.");
        }
    }

    private void validateCity(String city) {
        if (city == null) {
            throw new UseCaseException("City is null.");
        }
    }
}
