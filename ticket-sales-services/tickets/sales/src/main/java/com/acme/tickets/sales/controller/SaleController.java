package com.acme.tickets.sales.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.tickets.sales.business.service.SaleService;
import com.acme.tickets.sales.controller.dto.sale.CreateSaleDTO;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<SaleDTO> create(@RequestBody CreateSaleDTO payloadDTO) {
        return ResponseEntity.ok(service.create(payloadDTO));

    }

    @GetMapping("id/{id}")
    public ResponseEntity<SaleDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("event-sales/{eventId}")
    public ResponseEntity<List<SaleDTO>> getEventSales(@PathVariable UUID eventId) {
        return ResponseEntity.ok(service.getEventSales(eventId));
    }

    @GetMapping("user-sales/{userId}")
    public ResponseEntity<List<SaleDTO>> getUserSales(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getUserSales(userId));
    }

}
