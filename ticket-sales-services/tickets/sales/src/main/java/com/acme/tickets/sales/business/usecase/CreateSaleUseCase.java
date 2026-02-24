package com.acme.tickets.sales.business.usecase;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.acme.tickets.sales.business.service.clients.UserServiceClient;
import com.acme.tickets.sales.controller.dto.sale.CreateSaleDTO;
import com.acme.tickets.sales.exception.UseCaseException;
import com.acme.tickets.sales.infraestructure.entity.EventEntity;
import com.acme.tickets.sales.infraestructure.repository.IEventRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateSaleUseCase {

    private final IEventRepository repository;
    private final UserServiceClient userClient;

    public CreateSaleDTO validate(CreateSaleDTO dto) {

        validateEvent(dto.getEventId());
        validateUser(dto.getUserId());

        // @TODO: Additional validations
        // - Check if this event is on sale period
        // - Check if this event have available tickets

        return dto;
    }

    private void validateUser(UUID userId) {
        try {
            userClient.getById(userId);

        } catch (FeignException.BadRequest ex) {
            throw new UseCaseException("User not found.");
        }
        // } catch (FeignException ex) {
        // throw new UseCaseException("Error calling User Service");
        // }
    }

    private void validateEvent(UUID eventId) {
        Optional<EventEntity> event = repository.findById(eventId);
        if (event.isEmpty()) {
            throw new UseCaseException("Event not found.");
        }
    }

}
