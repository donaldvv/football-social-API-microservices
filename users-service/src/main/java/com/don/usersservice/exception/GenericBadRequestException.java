package com.don.usersservice.exception;

/**
 * @author Donald Veizi
 */
public class GenericBadRequestException extends RuntimeException {

    public GenericBadRequestException(final String message) {
        super(message);
    }
}
