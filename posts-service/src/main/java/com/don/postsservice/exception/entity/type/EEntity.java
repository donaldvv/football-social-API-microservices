package com.don.postsservice.exception.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Donald Veizi
 */
@Getter @RequiredArgsConstructor
public enum EEntity {

    // the reference entity: UserExt
    USER("USER"),
    POST("POST")

    ;

    private final String entityType;
}
