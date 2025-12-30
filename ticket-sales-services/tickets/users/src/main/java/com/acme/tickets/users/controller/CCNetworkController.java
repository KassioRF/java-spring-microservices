package com.acme.tickets.users.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tickets.users.dto.CCNetworkDTO;
import com.acme.tickets.users.dto.CreateCCNetworkDTO;
import com.acme.tickets.users.service.CCNetworkService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/ccn")
@AllArgsConstructor
public class CCNetworkController {

    private final CCNetworkService service;

    @PostMapping
    public ResponseEntity<CCNetworkDTO> create(@RequestBody CreateCCNetworkDTO dto) {
        CCNetworkDTO responseDto = service.create(dto);
        // @TODO: Handle with exceptions
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<CCNetworkDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/id/{ccnId}")
    public ResponseEntity<CCNetworkDTO> findById(@PathVariable(value = "ccnId") UUID id) {
        CCNetworkDTO responseDto = service.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/name/{ccnName}")
    public ResponseEntity<List<CCNetworkDTO>> findByName(@PathVariable(value = "ccnName") String name) {
        List<CCNetworkDTO> responseDto = service.findByName(name);
        return ResponseEntity.ok(responseDto);
    }

}
