package com.acme.tickets.users.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.tickets.users.converter.UserConverter;
import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.usecase.CreateUserUseCase;
import com.acme.tickets.users.domain.usecase.UpdateUserCreditCardUseCase;
import com.acme.tickets.users.domain.usecase.UpdateUserProfileUseCase;
import com.acme.tickets.users.dto.CreateUserDTO;
import com.acme.tickets.users.dto.DeleteUserDTO;
import com.acme.tickets.users.dto.UpdateUserCreditCardDTO;
import com.acme.tickets.users.dto.UpdateUserDTO;
import com.acme.tickets.users.entity.CreditCardNetworkEntity;
import com.acme.tickets.users.entity.UserEntity;
import com.acme.tickets.users.exception.UseCaseException;
import com.acme.tickets.users.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private CreateUserUseCase createUseCase;

    @Mock
    private UpdateUserProfileUseCase updateUserProfileUseCase;

    @Mock
    private UpdateUserCreditCardUseCase updateUserCreditCardUseCase;

    @InjectMocks
    private UserService service;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
    }

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserDTO dto = mock(CreateUserDTO.class);

        UserDomain domain = buildValidUserDomain();
        UserEntity entity = UserConverter.toEntity(domain);

        when(createUseCase.validate(any())).thenReturn(domain);
        when(repository.save(any())).thenReturn(entity);

        service.create(dto);

        verify(createUseCase).validate(any());
        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProfileAndUserNotFound() {
        UpdateUserDTO dto = mock(UpdateUserDTO.class);

        when(dto.id()).thenReturn(userId);
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class,
                () -> service.updateProfile(dto));
    }

    @Test
    void shouldUpdateUserProfileSuccessfully() {
        UpdateUserDTO dto = mock(UpdateUserDTO.class);

        when(dto.id()).thenReturn(userId);
        when(dto.name()).thenReturn("John");
        when(dto.city()).thenReturn("SP");

        UserEntity entity = minimalValidUserEntity();

        when(repository.findById(userId))
                .thenReturn(Optional.of(entity));
        when(repository.save(any()))
                .thenReturn(entity);

        service.updateProfile(dto);

        verify(updateUserProfileUseCase)
                .validate("John", "SP");
        verify(repository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingCreditCardAndUserNotFound() {
        UpdateUserCreditCardDTO dto = mock(UpdateUserCreditCardDTO.class);

        when(dto.userId()).thenReturn(userId);
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class,
                () -> service.updateCreditCard(dto));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnGetById() {
        when(repository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(UseCaseException.class,
                () -> service.getById(userId));
    }

    @Test
    void shouldUpdateUserCreditCardSuccessfully() {
        UpdateUserCreditCardDTO dto = mock(UpdateUserCreditCardDTO.class);

        when(dto.userId()).thenReturn(userId);
        when(dto.ccNetworkId()).thenReturn(UUID.randomUUID());
        when(dto.creditCardNumber()).thenReturn("4111111111111111");

        UserEntity entity = minimalValidUserEntity();
        UserDomain domain = buildValidUserDomain();

        when(repository.findById(userId))
                .thenReturn(Optional.of(entity));

        when(updateUserCreditCardUseCase.validate(
                any(UserDomain.class),
                any(UUID.class),
                any(String.class)))
                        .thenReturn(domain);

        when(repository.save(any()))
                .thenReturn(entity);

        service.updateCreditCard(dto);

        verify(updateUserCreditCardUseCase)
                .validate(any(), any(), any());
        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenDeletingWithInvalidPassword() {
        DeleteUserDTO dto = mock(DeleteUserDTO.class);

        UserEntity entity = minimalValidUserEntity();
        entity.setPassword("correct");

        when(dto.id()).thenReturn(userId);
        when(dto.password()).thenReturn("wrong");
        when(repository.findById(userId))
                .thenReturn(Optional.of(entity));

        assertThrows(UseCaseException.class,
                () -> service.delete(dto));
    }

    // -------- helpers --------

    private UserEntity minimalValidUserEntity() {
        CreditCardNetworkEntity cc = new CreditCardNetworkEntity();
        cc.setId(UUID.randomUUID());

        UserEntity user = new UserEntity();
        user.setCreditCardNetwork(cc);
        user.setPassword("123");

        return user;
    }

    private UserDomain buildValidUserDomain() {
        CCNetworkDomain ccNetwork = CCNetworkDomain.builder()
                .id(UUID.randomUUID())
                .name("VISA")
                .build();

        return UserDomain.builder()
                .name("John Doe")
                .email("john@doe.com")
                .password("123456")
                .city("São Paulo")
                .creditCardNumber("4111111111111111")
                .ccNetwork(ccNetwork)
                .build();
    }
}
