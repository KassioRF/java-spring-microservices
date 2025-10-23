package com.acme.tickets.users.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.acme.tickets.users.enums.EnumUserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user within the ticket sales system.
 * <p>
 * This entity stores user information, including identification,
 * authentication data, and credit card details. Each user is associated
 * with a {@link CreditCardNetworkEntity}, defining the card brand (e.g., Visa, MasterCard).
 * </p>
 *
 * <p><b>Lifecycle:</b> 
 * The fields {@code createdAt} and {@code updatedAt}
 * are automatically managed by {@link PrePersist} and {@link PreUpdate} callbacks.
 * </p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EnumUserType role;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumber;

    @ManyToOne
    @JoinColumn(name = "credit_card_network_id", nullable = false)
    private CreditCardNetworkEntity creditCardNetwork;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforeSave() {
        if (this.active == null) {
            this.active = true;
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
