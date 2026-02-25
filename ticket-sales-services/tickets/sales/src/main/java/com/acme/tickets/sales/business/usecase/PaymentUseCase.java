package com.acme.tickets.sales.business.usecase;

import org.springframework.stereotype.Component;

import com.acme.tickets.sales.business.converter.SaleConverter;
import com.acme.tickets.sales.business.service.clients.PaymentServiceClient;
import com.acme.tickets.sales.business.service.clients.dtos.PaymentDTO;
import com.acme.tickets.sales.controller.dto.sale.ProcessSaleDTO;
import com.acme.tickets.sales.controller.dto.sale.SaleDTO;
import com.acme.tickets.sales.enums.EnumSaleStatus;
import com.acme.tickets.sales.exception.UseCaseException;
import com.acme.tickets.sales.infraestructure.entity.SaleEntity;
import com.acme.tickets.sales.infraestructure.repository.ISaleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentUseCase {
    private final ISaleRepository saleRepository;
    private final PaymentServiceClient paymentService;

    public SaleDTO processPayment(ProcessSaleDTO payloadDTO) {

        SaleEntity saleEntity = saleRepository.findById(payloadDTO.getSaleId())
                .orElseThrow(() -> new UseCaseException("Sale not found"));

        if (!saleEntity.getUserId().equals(payloadDTO.getUserId())) {
            throw new UseCaseException("Not Allowed");
        }

        if (saleEntity.getStatus() != EnumSaleStatus.OPEN) {
            throw new UseCaseException("Payment could not be processed. The order status is: "
                    + saleEntity.getStatus().name());
        }

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .saleId(saleEntity.getId())
                .userId(saleEntity.getUserId())
                .amount(saleEntity.getEvent().getPrice())
                .build();

        boolean paymentSuccess = paymentService.processPayment(paymentDTO);

        if (!paymentSuccess) {
            throw new UseCaseException("Payment failed");
        }

        saleEntity.setStatus(EnumSaleStatus.PAID);

        saleEntity = saleRepository.save(saleEntity);

        return SaleConverter.toDTO(saleEntity);

    }

    public SaleDTO cancelSale(ProcessSaleDTO payloadDTO) {

        SaleEntity saleEntity = saleRepository.findById(payloadDTO.getSaleId())
                .orElseThrow(() -> new UseCaseException("Sale not found"));

        if (!saleEntity.getUserId().equals(payloadDTO.getUserId())) {
            throw new UseCaseException("Not Allowed");
        }

        if (saleEntity.getStatus() == EnumSaleStatus.CANCELLED) {
            throw new UseCaseException("Payment has already been cancelled");
        }

        if (saleEntity.getStatus() == EnumSaleStatus.REFUNDED) {
            throw new UseCaseException("Payment has already been refunded");
        }

        if (saleEntity.getStatus() == EnumSaleStatus.OPEN) {
            saleEntity.setStatus(EnumSaleStatus.CANCELLED);
            saleEntity = saleRepository.save(saleEntity);
        }

        if (saleEntity.getStatus() == EnumSaleStatus.PAID) {
            boolean refundAccepted = paymentService.requestRefund(saleEntity.getId());
            if (refundAccepted) {
                saleEntity.setStatus(EnumSaleStatus.REFUNDED);
                saleEntity = saleRepository.save(saleEntity);
            }
        }

        return SaleConverter.toDTO(saleEntity);
    }

}
