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
import com.acme.tickets.users.domain.port.UserRepositoryPort;
import com.acme.tickets.users.enums.EnumUserRole;
import com.acme.tickets.users.exception.UseCaseException;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private CCNetworkRepositoryPort ccNetworkRepositoryPort;

    @InjectMocks
    private CreateUserUseCase useCase;

    private UserDomain validDomain;

    @BeforeEach
    void setup() {
        validDomain = buildValidUserDomain();
    }

    // ---- User Credit Card Infos ---
    @Test
    void shouldThrowExceptionWhenCCNetworkIsNull() {

        validDomain.setCcNetwork(null);
        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("Invalid Credit Card Network.", exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCCNetworkIdIsNull() {
        validDomain.getCcNetwork().setId(null);

        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("Credit Card Network ID is null.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCCNetworkDoesNotExist() {
        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("Credit Card Network ID does not exist.",
                exception.getMessage());
    }

    @Test
    void shloudThrowExceptionWhenCCNumberIsNull() {
        mockValidCCNetwork();

        validDomain.setCreditCardNumber(null);
        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("Credit Card Number is null.",
                exception.getMessage());
    }

    @Test
    void shouldReplaceCcNetworkWithPersistedDomain() {

        mockValidCCNetwork();

        CCNetworkDomain persistedCCNetwork = validDomain.getCcNetwork();
        UUID ccNetworkId = persistedCCNetwork.getId();

        when(userRepositoryPort.existsByEmail(validDomain.getEmail()))
                .thenReturn(false);

        when(ccNetworkRepositoryPort.findById(ccNetworkId))
                .thenReturn(Optional.of(persistedCCNetwork));

        useCase.validate(validDomain);

        assertEquals("Test CNNetwork", validDomain.getCcNetwork().getName());

    }

    // ---- User infos ---
    @Test
    void shouldThrowExceptionWhenNameIsNull() {

        mockValidCCNetwork();

        validDomain.setName(null);

        UseCaseException exception = assertThrows(
                UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("Name is null.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        mockValidCCNetwork();

        validDomain.setEmail(null);

        UseCaseException exception = assertThrows(UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("E-mail is null.", exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        mockValidCCNetwork();

        validDomain.setPassword(null);

        UseCaseException exception = assertThrows(UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("Password is null.", exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {
        mockValidCCNetwork();

        validDomain.setCity(null);

        UseCaseException exception = assertThrows(UseCaseException.class,
                () -> useCase.validate(validDomain));

        assertEquals("City is null.", exception.getMessage());
    }

    @Test
    void shouldSetDefaultRoleWhenRoleIsNull() {
        mockValidCCNetwork();
        validDomain.setRole(null);

        useCase.validate(validDomain);

        assertDoesNotThrow(() -> useCase.validate(validDomain));

        assertEquals(EnumUserRole.CUSTOMER, validDomain.getRole());
    }

    @Test
    void shouldKeepRoleWhenRoleIsProvided() {
        mockValidCCNetwork();

        validDomain.setRole(EnumUserRole.ADMIN);

        assertDoesNotThrow(() -> useCase.validate(validDomain));

        assertEquals(EnumUserRole.ADMIN, validDomain.getRole());
    }

    // ---------- helpers ----------

    private void mockValidCCNetwork() {
        when(ccNetworkRepositoryPort
                .findById(validDomain.getCcNetwork().getId()))
                        .thenReturn(Optional.of(validDomain.getCcNetwork()));
    }

    private UserDomain buildValidUserDomain() {

        CCNetworkDomain ccNetwork = CCNetworkDomain.builder()
                .id(UUID.randomUUID())
                .name("Test CNNetwork")
                .build();

        UserDomain user = UserDomain.builder()
                .name("John Doe")
                .email("jhon@doe.com")
                .password("123456")
                .city("São Paulo")
                .creditCardNumber("4111111111111111")
                .ccNetwork(ccNetwork)
                .role(null)
                .build();

        return user;
    }

}
