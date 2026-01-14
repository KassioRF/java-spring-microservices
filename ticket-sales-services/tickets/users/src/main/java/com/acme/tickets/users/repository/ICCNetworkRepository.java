package com.acme.tickets.users.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.acme.tickets.users.entity.CreditCardNetworkEntity;
import java.util.List;
import java.util.Optional;

public interface ICCNetworkRepository extends JpaRepository<CreditCardNetworkEntity, UUID> {

    List<CreditCardNetworkEntity> findByName(String name);

    List<CreditCardNetworkEntity> findByNameContainingIgnoreCase(String name);

    List<CreditCardNetworkEntity> findByDescription(String description);

    // Tax queries, just testing the sintax..
    @Query(value = "SELECT cce FROM CreditCardNetworkEntity cce " + "WHERE cce.tax = :tax")
    Optional<CreditCardNetworkEntity> searchByTax(@Param("tax") Double tax);

    // Using native query...
    @Query(value = " select * from credit_card_network " + " where tax = :tax ", nativeQuery = true)
    Optional<CreditCardNetworkEntity> searchByTaxNative(@Param("tax") Double tax);

}
