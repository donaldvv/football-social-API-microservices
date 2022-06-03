package com.don.usersservice.exception.badrequest;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Donald Veizi
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("INVALID_REFRESH_TOKEN")
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(final String message) {
        super(message);
    }

    public RefreshTokenException(final String message, String... args) {
        super(
                args.length != StringUtils.countMatches(message, "%s")
                        ? "Invalid refresh token"
                        : String.format(message, (Object[]) args)
        );
    }


}
