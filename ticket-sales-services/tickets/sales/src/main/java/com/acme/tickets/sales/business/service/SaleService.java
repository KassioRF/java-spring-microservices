package com.acme.tickets.sales.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.acme.tickets.sales.business.converter.EventConverter;
import com.acme.tickets.sales.business.converter.SaleConverter;
import com.acme.tickets.sales.controller.dto.event.EventDTO;
import com.acme.tickets.sales.controller.dto.sale.CreateSaleDTO;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;
import com.acme.tickets.sales.exception.ServiceException;
import com.acme.tickets.sales.infraestructure.entity.SaleEntity;
import com.acme.tickets.sales.infraestructure.repository.ISaleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final ISaleRepository repository;
    private final EventService eventService;

    public List<SaleDTO> getAll() {
        List<SaleEntity> items = repository.findAll();
        return items.stream()
                .map(SaleConverter::toDTO)
                .toList();
    }

    @Transactional
    public SaleDTO create(CreateSaleDTO dto) {

        // @TODO: Requires aditional validations:
        // - Check if user exists (Requires a Client comunicate with Users Application)
        // - Check if this event is on sale period
        // - Check if this event have available tickets

        // Check if event exists
        Optional<EventDTO> optionalEvent = eventService.getById(dto.getEventId());

        if (optionalEvent.isEmpty()) {
            throw new ServiceException("Event not found.");
        }

        EventDTO eventDTO = optionalEvent.get();

        // Save Sale
        SaleEntity entity = SaleConverter.toEntity(dto);

        entity.setEvent(EventConverter.toEntity(eventDTO));

        entity = repository.save(entity);

        return SaleConverter.toDTO(entity);

    }

    /**
     * TODO:
     * 
     * - Get Sales By EventId
     * - Process Sale (Confirm Payment and change status to PAID)
     * - Cancel Sale (Process the cancellation of the sale. Requires to
     * - refound payment then set status to REFUNDED)
     * 
     */

}
