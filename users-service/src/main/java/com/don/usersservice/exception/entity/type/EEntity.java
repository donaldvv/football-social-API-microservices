package com.don.usersservice.exception.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Donald Veizi
 */
@Getter @RequiredArgsConstructor
public enum EEntity {

    USER("USER"),
    REFRESH_TOKEN("REFRESH TOKEN"),
    ROLE("ROLE")


    ;

    private final String entityType;
}
