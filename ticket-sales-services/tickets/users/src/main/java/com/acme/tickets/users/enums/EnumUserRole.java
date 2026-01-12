package com.acme.tickets.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the different types of users within the system.
 * 
 * <p>
 * Each user type has a unique identifier and a descriptive label. This enum is typically
 * used for defining roles or permissions such as customer, enterprise (event organizer),
 * and administrator.
 * </p>
 *
 * <p>
 * <b>Usage example:</b>
 * </p>
 * 
 * <pre>{@code
 * 
 * EnumUserRole type = EnumUserRole.CUSTOMER;
 * System.out.println(type.getDescription()); // "Customer"
 * }</pre>
 */
@Getter
@AllArgsConstructor
public enum EnumUserRole {

    /** Regular customer who buys tickets */
    CUSTOMER(1, "Customer"),

    /** Enterprise or company that organizes events. */
    ENTERPRISE(2, "Enterprise"),

    /** System administrator with full access. */
    ADMIN(3, "Admin");

    private final int id;
    private final String description;

}
