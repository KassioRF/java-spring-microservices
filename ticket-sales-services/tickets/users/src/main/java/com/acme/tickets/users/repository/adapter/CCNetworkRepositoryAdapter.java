package com.acme.tickets.users.repository.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.users.converter.CCNetworkConverter;
import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.port.CCNetworkRepositoryPort;
import com.acme.tickets.users.entity.CreditCardNetworkEntity;
import com.acme.tickets.users.repository.ICCNetworkRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CCNetworkRepositoryAdapter implements CCNetworkRepositoryPort {

    private final ICCNetworkRepository repository;

    @Override
    public Optional<CCNetworkDomain> findById(UUID id) {
        Optional<CreditCardNetworkEntity> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        CCNetworkDomain domain = CCNetworkConverter.toDomain(entity.get());

        return Optional.of(domain);

    }

}
