package com.don.postsservice.exception.badrequest;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Donald Veizi
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("BAD_REQUEST") // add a custom code to the response. default is class name without the Exception part
public class GeneralBadRequest extends RuntimeException {

    public GeneralBadRequest(final String message) {
        super(message);
    }

    public GeneralBadRequest(final String message, final String... args) {
        super(
                args.length != StringUtils.countMatches(message, "%s")
                        ? "General Conflict error - please provide another request"
                        : String.format(message, (Object[]) args)
        );
    }
}
