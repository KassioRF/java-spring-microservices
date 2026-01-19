package com.acme.tickets.sales.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumSaleStatus {

    OPEN(1, "OPEN"),

    PAID(2, "PAID"),

    CANCELLED(3, "CANCELLED"),

    REFUNDED(4, "REFUNDED");

    private Integer id;
    private String description;
}
