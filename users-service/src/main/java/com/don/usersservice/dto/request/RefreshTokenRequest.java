package com.don.usersservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Donald Veizi
 */
@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}
