package com.acme.tickets.users.converter;

import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.dto.CCNetworkDTO;
import com.acme.tickets.users.dto.CreateCCNetworkDTO;
import com.acme.tickets.users.entity.CreditCardNetworkEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CCNetworkConverter {

    // DTO to Domain
    public static CCNetworkDomain toDomain(CreateCCNetworkDTO dto) {
        return CCNetworkDomain.builder().name(dto.name()).build();
    }

    // Entity to Domain
    public static CCNetworkDomain toDomain(CreditCardNetworkEntity entity) {
        return CCNetworkDomain.builder().id(entity.getId()).name(entity.getName()).build();
    }

    // Domain to Entity (JPA)
    public static CreditCardNetworkEntity toEntity(CCNetworkDomain domain) {
        return CreditCardNetworkEntity.builder().id(domain.getId()).name(domain.getName()).build();
    }

    // Entity to DTO
    public static CCNetworkDTO toDto(CreditCardNetworkEntity entity) {
        return new CCNetworkDTO(entity.getId(), entity.getName());
    }

}
