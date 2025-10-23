package com.acme.tickets.users.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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

/**
 * Represents a credit card network (e.g., Visa, MasterCard, etc.).
 * 
 * <p>
 * This entity serves as a reference for users' credit card information.
 * It supports a <b>soft delete</b> policy, meaning that when a record is
 * deleted, it is not physically removed from the database. Instead,
 * the {@code deletedAt} field is populated with the current timestamp,
 * and the entity is automatically excluded from queries by the
 * {@link org.hibernate.annotations.SQLRestriction} filter.
 * </p>
 *
 * <p><b>Soft delete behavior:</b></p>
 * <ul>
 *   <li>Deleting via repository or EntityManager triggers 
 *   {@code UPDATE credit_card_networks SET deleted_at = NOW()}.</li>
 *   <li>Queries automatically ignore rows where {@code deleted_at IS NOT NULL}.</li>
 *   <li>Physical deletion can still be performed manually if required.</li>
 * </ul>
 *
 * <p><b>Auditing:</b> The fields {@code createdAt} and {@code updatedAt}
 * are managed automatically via {@link jakarta.persistence.PrePersist}
 * and {@link jakarta.persistence.PreUpdate} lifecycle hooks.</p>
 */
@Entity
@Table(name = "credit_card_networks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE credit_card_networks SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CreditCardNetworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @PrePersist
    public void beforeSave() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
