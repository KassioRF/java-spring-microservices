package com.acme.tickets.sales.business.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.sales.business.converter.SaleConverter;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;
import com.acme.tickets.sales.infraestructure.entity.SaleEntity;
import com.acme.tickets.sales.infraestructure.repository.ISaleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListUserSalesUseCase {

    private final ISaleRepository saleRepository;

    public List<SaleDTO> execute(UUID userId) {
        List<SaleEntity> salesEntities = saleRepository.findByUserId(userId);

        return salesEntities.stream()
                .map(SaleConverter::toDTO)
                .toList();
    }
}
