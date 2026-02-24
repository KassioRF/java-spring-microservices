package com.acme.tickets.sales.business.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.acme.tickets.sales.business.converter.EventConverter;
import com.acme.tickets.sales.controller.dto.event.CreateEventDTO;
import com.acme.tickets.sales.controller.dto.event.DeleteEventDTO;
import com.acme.tickets.sales.controller.dto.event.EventDTO;
import com.acme.tickets.sales.controller.dto.event.UpdateEventDTO;
import com.acme.tickets.sales.enums.EnumEventType;
import com.acme.tickets.sales.exception.ServiceException;
import com.acme.tickets.sales.infraestructure.entity.EventEntity;
import com.acme.tickets.sales.infraestructure.repository.IEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * @TODO
 *       - List by Category
 *       - List by Organizer
 */
public class EventService {

    private final IEventRepository repository;

    public EventDTO create(CreateEventDTO dto) {

        // @TODO: Validate if the organizer User exists on Users Service!
        // - via organizerId
        EventEntity entity = EventConverter.toEntity(dto);

        entity = repository.save(entity);
        return EventConverter.toDTO(entity);

    }

    public EventDTO update(UUID eventId, UpdateEventDTO dto) {
        // validate if authenticated user is the organizer or admin
        Optional<EventEntity> optionalEntity = repository.findById(eventId);

        if (optionalEntity.isEmpty()) {
            throw new ServiceException("Event not found.");
        }

        EventEntity entity = optionalEntity.get();

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getType() != null) {
            entity.setType(dto.getType());
        }

        if (dto.getAllotedTickets() != null) {
            entity.setAllotedTickets(dto.getAllotedTickets());
        }

        if (dto.getEventDate() != null) {
            entity.setEventDate(dto.getEventDate());
        }

        if (dto.getExpectedDuration() != null) {
            entity.setExpectedDuration(dto.getExpectedDuration());
        }

        if (dto.getStartingSales() != null) {
            entity.setStartingSales(dto.getStartingSales());
        }

        if (dto.getEndingSales() != null) {
            entity.setEndingSales(dto.getEndingSales());
        }

        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }

        entity = repository.save(entity);

        return EventConverter.toDTO(entity);

    }

    public void delete(DeleteEventDTO deleteEventDTO) {

        // validate organizerId or Admin to be allowed to delete

        if (!repository.existsById(deleteEventDTO.eventId())) {
            throw new ServiceException("Event not found.");
        }

        repository.deleteById(deleteEventDTO.eventId());
    }

    public List<EventDTO> getAll() {
        List<EventEntity> query = repository.findAll();

        return query.stream()
                .map(EventConverter::toDTO)
                .toList();

    }

    public Optional<EventDTO> getById(UUID id) {
        // validate if authenticated user is the organizer or admin
        Optional<EventEntity> entity = repository.findById(id);

        if (entity.isEmpty()) {
            throw new ServiceException("Event not found.");
        }

        EventDTO dto = EventConverter.toDTO(entity.get());

        return Optional.of(dto);

    }

    public List<EventDTO> findAllByName(String name) {
        List<EventEntity> entities = repository.findAllByNameContainingIgnoreCase(name);
        return entities.stream()
                .map(EventConverter::toDTO)
                .toList();

    }

    public List<EventDTO> findAllByType(EnumEventType type) {
        List<EventEntity> entities = repository.findAllByType(type);
        return entities.stream()
                .map(EventConverter::toDTO)
                .toList();
    }

}
