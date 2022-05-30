package com.don.usersservice.exception;

/**
 * @author Donald Veizi
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
