package com.acme.tickets.sales.controller.dto.sale;

import java.util.UUID;

import com.acme.tickets.sales.enums.EnumSaleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSaleStatusDTO {
    private UUID saleId;
    private EnumSaleStatus status;
}
