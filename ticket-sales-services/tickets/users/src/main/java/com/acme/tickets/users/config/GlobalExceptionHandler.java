package com.acme.tickets.users.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.acme.tickets.users.exception.ServiceException;
import com.acme.tickets.users.exception.UseCaseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UseCaseException.class)
    public ResponseEntity<String> handleUseCaseException(UseCaseException useCaseException) {
        String message = "[" + useCaseException.getClass().getSimpleName() + " ]";
        message += " Error: " + useCaseException.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException serviceException) {
        String message = "[" + serviceException.getClass().getSimpleName() + " ]";
        message += " Error: " + serviceException.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
