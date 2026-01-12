package com.acme.tickets.users.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.acme.tickets.users.enums.EnumUserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDomain {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String city;

    private String creditCardNumber;
    private CCNetworkDomain ccNetwork;

    private EnumUserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
