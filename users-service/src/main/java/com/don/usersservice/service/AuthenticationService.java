package com.don.usersservice.service;

import com.don.usersservice.dto.request.LoginRequest;
import com.don.usersservice.dto.request.RefreshTokenRequest;
import com.don.usersservice.dto.response.JwtResponse;
import com.don.usersservice.dto.response.RefreshTokenResponse;

/**
 * @author Donald Veizi
 */
public interface AuthenticationService {

    /**
     * Authenticates the user
     * @param loginRequest the request that contains credentials
     * @return {@link JwtResponse}
     */
    JwtResponse authenticateUser(final LoginRequest loginRequest);

    /**
     * Provides a new access token
     * @param request {@link RefreshTokenRequest}
     * @return {@link RefreshTokenResponse}
     */
    RefreshTokenResponse getRefreshedAccessToken(final RefreshTokenRequest request);

    /**
     * Will make the refresh token invalid - expired
     * @param request {@link RefreshTokenRequest}
     * @return success message
     */
    String expireRefreshToken(final RefreshTokenRequest request);
}
