package com.acme.tickets.sales.business.converter;

import com.acme.tickets.sales.controller.dto.sale.CreateSaleDTO;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;
import com.acme.tickets.sales.infraestructure.entity.EventEntity;
import com.acme.tickets.sales.infraestructure.entity.SaleEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleConverter {

    public static SaleDTO toDTO(SaleEntity entity) {
        return SaleDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .event(EventConverter.toDTO(entity.getEvent()))
                .status(entity.getStatus())
                .dateTime(entity.getDateTime())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static SaleEntity toEntity(CreateSaleDTO dto) {
        return SaleEntity.builder()
                .userId(dto.getUserId())
                .event(EventEntity.builder().id(dto.getEventId()).build())
                .build();
    }

}
