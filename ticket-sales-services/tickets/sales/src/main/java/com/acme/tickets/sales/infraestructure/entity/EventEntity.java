package com.acme.tickets.sales.infraestructure.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.acme.tickets.sales.enums.EnumEventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private UUID organizerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private EnumEventType type;

    @Column(nullable = false)
    private Integer allotedTickets;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = true)
    private LocalDateTime expectedDuration;

    @Column(nullable = false)
    private Float price;

    private LocalDateTime startingSales;
    private LocalDateTime endingSales;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforeSave() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
