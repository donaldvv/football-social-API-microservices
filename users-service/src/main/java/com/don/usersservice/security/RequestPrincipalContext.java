package com.don.usersservice.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Donald Veizi
 */
@Getter @Setter
public class RequestPrincipalContext {
    private long userId;
    private String userEmail;
}
