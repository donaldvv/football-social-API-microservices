package com.don.postsservice.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Donald Veizi
 */
@Getter @RequiredArgsConstructor
public enum EErrorMessage {

    REFRESH_TOKEN_NOT_FOUND("Refresh token not found in the database"),
    REFRESH_TOKEN_EXPIRED("Refresh token has expired"),

    FORBIDDEN_DELETE_ACCOUNT("User does not have permission to delete this account"),

    CONFLICT_EMAIL_TAKEN("Account with email %s, already exists. Provide another request with different email")


    ;

    private final String message;
}
