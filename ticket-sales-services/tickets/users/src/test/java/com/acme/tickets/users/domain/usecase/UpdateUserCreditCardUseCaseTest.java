package com.acme.tickets.users.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.port.CCNetworkRepositoryPort;
import com.acme.tickets.users.exception.UseCaseException;

@ExtendWith(MockitoExtension.class)
class UpdateUserCreditCardUseCaseTest {

    @Mock
    private CCNetworkRepositoryPort ccNetworkRepositoryPort;

    @InjectMocks
    private UpdateUserCreditCardUseCase useCase;

    private UserDomain user;
    private UUID ccNetworkId;

    @BeforeEach
    void setup() {
        ccNetworkId = UUID.randomUUID();

        user = UserDomain.builder()
                .name("John")
                .creditCardNumber("1111")
                .build();
    }

    @Test
    void shouldThrowExceptionWhenCCNetworkIdIsNull() {
        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(user, null, "4111111111111111"));

        assertEquals("Invalid Credit Card Network.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCCNetworkDoesNotExist() {
        when(ccNetworkRepositoryPort.findById(ccNetworkId))
                .thenReturn(Optional.empty());

        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(user, ccNetworkId, "4111111111111111"));

        assertEquals(
                "Credit Card Network ID does not exist.",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreditCardNumberIsNull() {
        CCNetworkDomain ccNetwork = CCNetworkDomain.builder()
                .id(ccNetworkId)
                .name("VISA")
                .build();

        when(ccNetworkRepositoryPort.findById(ccNetworkId))
                .thenReturn(Optional.of(ccNetwork));

        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(user, ccNetworkId, null));

        assertEquals("Credit Card Number is null.",
                exception.getMessage());
    }

    @Test
    void shouldUpdateCreditCardAndNetworkWhenValid() {
        CCNetworkDomain ccNetwork = CCNetworkDomain.builder()
                .id(ccNetworkId)
                .name("VISA")
                .build();

        when(ccNetworkRepositoryPort.findById(ccNetworkId))
                .thenReturn(Optional.of(ccNetwork));

        assertDoesNotThrow(() -> useCase.validate(user, ccNetworkId, "4111111111111111"));

        assertEquals("4111111111111111",
                user.getCreditCardNumber());
        assertEquals("VISA",
                user.getCcNetwork().getName());
    }
}
