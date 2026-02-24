package com.acme.tickets.sales.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
