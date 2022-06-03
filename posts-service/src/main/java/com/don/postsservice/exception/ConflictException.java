package com.don.postsservice.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Donald Veizi
 */

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
