package com.acme.tickets.sales.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumEventType {

    PALESTRA(1, "PALESTRA"),

    SHOW(2, "SHOW"),

    TEATRO(3, "TEATRO"),

    CURSO(4, "CURSO"),

    GERAL(5, "GERAL");

    private Integer id;
    private String description;

    public static EnumEventType getById(Integer id) {
        for (EnumEventType type : EnumEventType.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid EnumEventType id: " + id);
    }

}
