package com.acme.tickets.sales.business.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.acme.tickets.sales.business.converter.EventConverter;
import com.acme.tickets.sales.business.converter.SaleConverter;
import com.acme.tickets.sales.business.usecase.CreateSaleUseCase;
import com.acme.tickets.sales.business.usecase.ListEventSalesUseCase;
import com.acme.tickets.sales.business.usecase.ListUserSalesUseCase;
import com.acme.tickets.sales.controller.dto.event.EventDTO;
import com.acme.tickets.sales.controller.dto.sale.CreateSaleDTO;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;
import com.acme.tickets.sales.enums.EnumSaleStatus;
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
    private final CreateSaleUseCase createUseCase;
    private final ListEventSalesUseCase listEventSalesUseCase;
    private final ListUserSalesUseCase listUserSalesUseCase;

    public List<SaleDTO> getAll() {
        List<SaleEntity> items = repository.findAll();
        return items.stream()
                .map(SaleConverter::toDTO)
                .toList();
    }

    @Transactional
    public SaleDTO create(CreateSaleDTO payloadDTO) {

        // validate
        CreateSaleDTO validatedDto = createUseCase.validate(payloadDTO);
        // get Event DTO

        Optional<EventDTO> optionalEvent = eventService.getById(validatedDto.getEventId());

        if (optionalEvent.isEmpty()) {
            throw new ServiceException("Event not found.");
        }

        EventDTO eventDTO = optionalEvent.get();

        SaleEntity entity = SaleConverter.toEntity(validatedDto);

        entity.setEvent(EventConverter.toEntity(eventDTO));
        entity.setStatus(EnumSaleStatus.OPEN);

        // Save Sale
        entity = repository.save(entity);

        return SaleConverter.toDTO(entity);

    }

    public SaleDTO getById(UUID uuid) {
        Optional<SaleEntity> entity = repository.findById(uuid);

        if (entity.isEmpty()) {
            throw new ServiceException("Sale not found.");
        }

        SaleDTO dto = SaleConverter.toDTO(entity.get());

        return dto;

    }

    /**
     * TODO:
     * 
     * - [x] Get Event Sales
     * - [ ] Get User Sales
     * - [ ] Process Sale (Confirm Payment and change status to PAID)
     * - [ ] Cancel Sale (Process the cancellation of the sale. Requires to
     * - [ ] refound payment then set status to REFUNDED)
     * 
     */

    public List<SaleDTO> getEventSales(UUID eventId) {
        return listEventSalesUseCase.execute(eventId);
    }

    public List<SaleDTO> getUserSales(UUID userId) {
        return listUserSalesUseCase.execute(userId);
    }

}
