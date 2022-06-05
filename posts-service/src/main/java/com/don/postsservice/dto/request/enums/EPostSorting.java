package com.don.postsservice.dto.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The possible values that can be used to sort the requested Posts
 *
 * @author Donald Veizi
 */
@RequiredArgsConstructor @Getter
public enum EPostSorting {

    // the postFieldName should be changed if these values change in Post entity
    LIKES("likeCount"),
    RECENT("timeCreated")
    ;

    private final String postFieldName;
}
