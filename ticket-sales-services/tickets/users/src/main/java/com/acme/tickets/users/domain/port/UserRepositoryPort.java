package com.acme.tickets.users.domain.port;

import java.util.UUID;

import com.acme.tickets.users.domain.UserDomain;

public interface UserRepositoryPort extends RepositoryPort<UserDomain, UUID> {

    boolean existsByEmail(String email);

}
