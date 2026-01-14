package com.acme.tickets.users.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.acme.tickets.users.converter.CCNetworkConverter;
import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.dto.CCNetworkDTO;
import com.acme.tickets.users.dto.CreateCCNetworkDTO;
import com.acme.tickets.users.entity.CreditCardNetworkEntity;
import com.acme.tickets.users.exception.ServiceException;
import com.acme.tickets.users.repository.ICCNetworkRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CCNetworkService {

    private final ICCNetworkRepository repository;

    // Create
    public CCNetworkDTO create(CreateCCNetworkDTO dto) {

        // DTO to Domain
        CCNetworkDomain domain = CCNetworkConverter.toDomain(dto);
        if (domain.getName() == null || domain.getName().isBlank()) {
            return null;
        }

        // Domain to Entity
        CreditCardNetworkEntity entity = CCNetworkConverter.toEntity(domain);

        // Save
        CCNetworkDTO new_item = CCNetworkConverter.toDto(repository.save(entity));
        return new_item;

    }

    // Get All
    public List<CCNetworkDTO> getAll() {
        List<CreditCardNetworkEntity> items = repository.findAll();
        return items.stream().map(CCNetworkConverter::toDto).toList();
    }

    // Get by id
    public CCNetworkDTO findById(UUID id) {
        Optional<CreditCardNetworkEntity> qResult = repository.findById(id);

        if (qResult.isEmpty()) {
            throw new ServiceException("Credit Card Network ID dos not exist");
        }

        CreditCardNetworkEntity entity = qResult.get();

        CCNetworkDTO resultDto = CCNetworkConverter.toDto(entity);

        return resultDto;
    }

    // Get by Name
    public List<CCNetworkDTO> findByName(String name) {
        List<CreditCardNetworkEntity> qResult = repository.findByNameContainingIgnoreCase(name);
        return qResult.stream().map(CCNetworkConverter::toDto).toList();
    }

    // Update

    // Delete

}
