package com.acme.tickets.users.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.acme.tickets.users.converter.UserConverter;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.usecase.CreateUserUseCase;
import com.acme.tickets.users.domain.usecase.UpdateUserCreditCardUseCase;
import com.acme.tickets.users.domain.usecase.UpdateUserProfileUseCase;
import com.acme.tickets.users.dto.CreateUserDTO;
import com.acme.tickets.users.dto.DeleteUserDTO;
import com.acme.tickets.users.dto.UpdateUserCreditCardDTO;
import com.acme.tickets.users.dto.UpdateUserDTO;
import com.acme.tickets.users.dto.UserDTO;
import com.acme.tickets.users.entity.UserEntity;
import com.acme.tickets.users.exception.UseCaseException;
import com.acme.tickets.users.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository repository;
    private final CreateUserUseCase createUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final UpdateUserCreditCardUseCase updateUserCreditCardUseCase;

    public UserDTO create(CreateUserDTO payloadDTO) {

        UserDomain domain = UserConverter.toDomain(payloadDTO);
        UserDomain validatedDomain = createUseCase.validate(domain);
        UserEntity entity = repository.save(UserConverter.toEntity(validatedDomain));

        return UserConverter.toDto(entity);

    }

    public UserDTO updateProfile(UpdateUserDTO payloadDTO) {

        Optional<UserEntity> entityOptional = repository.findById(payloadDTO.id());

        if (entityOptional.isEmpty()) {
            throw new UseCaseException("User not found");
        }

        UserEntity entity = entityOptional.get();

        updateUserProfileUseCase.validate(payloadDTO.name(), payloadDTO.city());

        entity.setName(payloadDTO.name());
        entity.setCity(payloadDTO.city());

        repository.save(entity);

        return UserConverter.toDto(entity);
    }

    public UserDTO updateCreditCard(UpdateUserCreditCardDTO payloadDTO) {
        Optional<UserEntity> entityOptional = repository.findById(payloadDTO.userId());

        if (entityOptional.isEmpty()) {
            throw new UseCaseException("User not found");
        }

        UserEntity entity = entityOptional.get();
        UserDomain domain = UserConverter.toDomain(entity);

        UserDomain validatedDomain = updateUserCreditCardUseCase.validate(
                domain,
                payloadDTO.ccNetworkId(),
                payloadDTO.creditCardNumber());

        entity = repository.save(UserConverter.toEntity(validatedDomain));

        return UserConverter.toDto(entity);

    }

    public Optional<UserDTO> getById(UUID id) {

        Optional<UserEntity> entity = repository.findById(id);

        if (entity.isEmpty()) {
            throw new UseCaseException("User not found");
        }

        UserDTO dto = UserConverter.toDto(entity.get());
        return Optional.of(dto);

    }

    public List<UserDTO> getByName(String name) {

        List<UserEntity> usersEntities = repository
                .findAllByNameContainingIgnoreCase(name);

        return usersEntities.stream()
                .map(UserConverter::toDto)
                .toList();
    }

    public List<UserDTO> getAll() {

        List<UserEntity> usersEntities = repository.findAll();

        return usersEntities.stream()
                .map(UserConverter::toDto)
                .toList();
    }

    public void delete(DeleteUserDTO payloadDTO) {

        // TODO: Move to DeleteUserUseCase when security is implemented
        Optional<UserEntity> entityOptional = repository.findById(payloadDTO.id());

        if (entityOptional.isEmpty()) {
            throw new UseCaseException("User not found");
        }

        UserEntity entity = entityOptional.get();

        if (!entity.getPassword().equals(payloadDTO.password())) {
            throw new UseCaseException("Invalid password");
        }

        repository.deleteById(payloadDTO.id());

    }

}
