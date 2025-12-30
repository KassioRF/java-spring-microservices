package com.acme.tickets.users.exception;

public class UseCaseException extends RuntimeException {
    public UseCaseException(String message) {
        super(message);
    }
}
