package com.acme.tickets.sales.infraestructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.tickets.sales.enums.EnumEventType;
import com.acme.tickets.sales.infraestructure.entity.EventEntity;

public interface IEventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByName(String name);

    List<EventEntity> findAllByNameContainingIgnoreCase(String name);

    List<EventEntity> findAllByType(EnumEventType type);
}
