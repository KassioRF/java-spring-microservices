package com.acme.tickets.users.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.acme.tickets.users.converter.UserConverter;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.usecase.CreateUserUseCase;
import com.acme.tickets.users.dto.CreateUserDTO;
import com.acme.tickets.users.dto.DeleteUserDTO;
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

    public UserDTO create(CreateUserDTO payloadDTO) {

        UserDomain domain = UserConverter.toDomain(payloadDTO);

        createUseCase.setDomain(domain);

        createUseCase.validate();

        UserEntity entity = repository.save(UserConverter.toEntity(domain));

        return UserConverter.toDto(entity);
    }

    public UserDTO update(UpdateUserDTO payloadDTO) {

        Optional<UserEntity> entityOptional = repository.findById(payloadDTO.id());

        if (entityOptional.isEmpty()) {
            throw new UseCaseException("User not found");
        }

        UserEntity entity = entityOptional.get();

        entity.setName(payloadDTO.name());
        entity.setCity(payloadDTO.city());

        repository.save(entity);

        return UserConverter.toDto(entity);
    }

    public Optional<UserDTO> getById(UUID id) {

        Optional<UserEntity> entity = repository.findById(id);

        if (entity.isEmpty()) {
            // return Optional.empty();
            throw new UseCaseException("User not found");
        }

        UserDTO dto = UserConverter.toDto(entity.get());
        return Optional.of(dto);

    }

    public List<UserDTO> getByName(String name) {

        List<UserEntity> usersEntities = repository.findAllByNameContainingIgnoreCase(name);

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
