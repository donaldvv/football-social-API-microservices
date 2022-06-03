package com.don.usersservice.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Donald Veizi
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
@ResponseErrorCode("ACTION_FORBIDDEN") // add a custom code to the response. default is class name without the Exception part
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(final String message) {
        super(message);
    }
}
