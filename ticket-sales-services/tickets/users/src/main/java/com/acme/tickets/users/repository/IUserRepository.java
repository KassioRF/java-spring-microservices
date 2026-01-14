package com.acme.tickets.users.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.acme.tickets.users.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    List<UserEntity> findAllByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM tb_users u WHERE u.name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<UserEntity> findAllNameLike(@Param("name") String name);

}
