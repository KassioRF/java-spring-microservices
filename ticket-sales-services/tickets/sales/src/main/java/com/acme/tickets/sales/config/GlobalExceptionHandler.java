package com.acme.tickets.sales.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.acme.tickets.sales.exception.ServiceException;
import com.acme.tickets.sales.exception.UseCaseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ServiceException.class, UseCaseException.class })
    public ResponseEntity<String> handleServiceException(RuntimeException serviceException) {
        String message = "[" + serviceException.getClass().getSimpleName() + "]";
        message += " Error: " + serviceException.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
