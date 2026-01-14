package com.acme.tickets.users.converter;

import com.acme.tickets.users.domain.CCNetworkDomain;
import com.acme.tickets.users.domain.UserDomain;
import com.acme.tickets.users.dto.CreateUserDTO;
import com.acme.tickets.users.dto.UserDTO;
import com.acme.tickets.users.entity.UserEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    // DTO to Domain
    public static UserDomain toDomain(CreateUserDTO dto) {
        CCNetworkDomain ccNetworkDomain = CCNetworkDomain.builder()
                .id(dto.ccNetworkId())
                .build();

        return UserDomain.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .creditCardNumber(dto.creditCardNumber())
                .ccNetwork(ccNetworkDomain)
                .city(dto.city()).build();
    }

    // Entity to Domain
    public static UserDomain toDomain(UserEntity entity) {
        return UserDomain.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .city(entity.getCity())
                .creditCardNumber(entity.getCreditCardNumber())
                .ccNetwork(CCNetworkConverter.toDomain(entity.getCreditCardNetwork()))
                .active(entity.getActive())
                .role(entity.getRole())
                .build();
    }

    // Domain to Entity (JPA)
    public static UserEntity toEntity(UserDomain domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .city(domain.getCity())
                .creditCardNumber(domain.getCreditCardNumber())
                .creditCardNetwork(CCNetworkConverter.toEntity(domain.getCcNetwork()))
                .active(domain.getActive())
                .role(domain.getRole())
                .build();
    }

    // Domain to DTO
    public static UserDTO toDto(UserDomain domain) {
        return new UserDTO(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.getCity());
    }

    // Entity to DTO
    public static UserDTO toDto(UserEntity entity) {
        UserDomain domain = toDomain(entity);
        return toDto(domain);
    }

}
