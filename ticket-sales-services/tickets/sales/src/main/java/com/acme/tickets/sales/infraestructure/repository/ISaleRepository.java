package com.acme.tickets.sales.infraestructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.tickets.sales.infraestructure.entity.EventEntity;
import com.acme.tickets.sales.infraestructure.entity.SaleEntity;

public interface ISaleRepository extends JpaRepository<SaleEntity, UUID> {

    List<SaleEntity> findByEvent(EventEntity event);

}
