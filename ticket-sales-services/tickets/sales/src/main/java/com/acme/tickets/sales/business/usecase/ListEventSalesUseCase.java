package com.acme.tickets.sales.business.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.sales.business.converter.SaleConverter;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;
import com.acme.tickets.sales.exception.UseCaseException;
import com.acme.tickets.sales.infraestructure.entity.EventEntity;
import com.acme.tickets.sales.infraestructure.entity.SaleEntity;
import com.acme.tickets.sales.infraestructure.repository.IEventRepository;
import com.acme.tickets.sales.infraestructure.repository.ISaleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListEventSalesUseCase {

    private final IEventRepository eventRepository;
    private final ISaleRepository saleRepository;

    public List<SaleDTO> execute(UUID eventId) {

        Optional<EventEntity> eventEntity = eventRepository.findById(eventId);

        if (eventEntity.isEmpty()) {
            throw new UseCaseException("Event not found");

        }

        List<SaleEntity> salesEntities = saleRepository.findByEvent(eventEntity.get());

        return salesEntities.stream()
                .map(SaleConverter::toDTO)
                .toList();

    }

}
