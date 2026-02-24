package com.acme.tickets.sales.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tickets.sales.business.service.EventService;
import com.acme.tickets.sales.controller.dto.event.CreateEventDTO;
import com.acme.tickets.sales.controller.dto.event.DeleteEventDTO;
import com.acme.tickets.sales.controller.dto.event.EventDTO;
import com.acme.tickets.sales.controller.dto.event.UpdateEventDTO;
import com.acme.tickets.sales.enums.EnumEventType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody CreateEventDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PatchMapping("id/{eventId}")
    public ResponseEntity<EventDTO> update(@PathVariable(value = "eventId") UUID eventId,
            @RequestBody UpdateEventDTO dto) {
        return ResponseEntity.ok(service.update(eventId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteEventDTO deleteEventDTO) {
        service.delete(deleteEventDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("id/{eventId}")
    public ResponseEntity<EventDTO> getById(@PathVariable(value = "eventId") UUID id) {
        Optional<EventDTO> dto = service.getById(id);

        if (dto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto.get());
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<EventDTO>> findAllByName(@PathVariable String name) {
        List<EventDTO> eventsDTO = service.findAllByName(name);
        return ResponseEntity.ok(eventsDTO);
    }

    @GetMapping("type/{type}")
    public ResponseEntity<List<EventDTO>> findAllByType(
            @PathVariable EnumEventType type) {
        List<EventDTO> eventsDTO = service.findAllByType(type);
        return ResponseEntity.ok(eventsDTO);
    }

}
