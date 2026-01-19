package com.acme.tickets.sales.infraestructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.tickets.sales.infraestructure.entity.SaleEntity;

public interface ISaleRepository extends JpaRepository<SaleEntity, UUID> {

}
