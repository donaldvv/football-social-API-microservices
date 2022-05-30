package com.don.usersservice.exception;

/**
 * @author Donald Veizi
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
