package com.don.postsservice.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Donald Veizi
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    public ConflictException(final String message) {
        super(message);
    }

    public ConflictException(final String message, final String... args) {
        super(
                args.length != StringUtils.countMatches(message, "%s")
                        ? "General Conflict error - please provide another request"
                        : String.format(message, (Object[]) args)
        );
    }

}
