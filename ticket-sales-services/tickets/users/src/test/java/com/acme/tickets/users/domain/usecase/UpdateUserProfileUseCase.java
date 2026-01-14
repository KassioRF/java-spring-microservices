package com.acme.tickets.users.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.tickets.users.exception.UseCaseException;

@ExtendWith(MockitoExtension.class)
class UpdateUserProfileUseCaseTest {

    @InjectMocks
    private UpdateUserProfileUseCase useCase;

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(null, "São Paulo"));

        assertEquals("Name is null.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {
        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate("John", null));

        assertEquals("City is null.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenNameAndCityAreValid() {
        assertDoesNotThrow(() -> useCase.validate("John", "São Paulo"));
    }
}
