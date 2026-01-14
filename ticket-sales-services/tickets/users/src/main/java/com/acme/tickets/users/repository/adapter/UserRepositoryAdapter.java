package com.acme.tickets.users.repository.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.users.converter.UserConverter;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.domain.port.UserRepositoryPort;
import com.acme.tickets.users.entity.UserEntity;
import com.acme.tickets.users.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final IUserRepository repository;

    @Override
    public Optional<UserDomain> findById(UUID id) {
        Optional<UserEntity> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        UserDomain domain = UserConverter.toDomain(entity.get());

        return Optional.of(domain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
}
