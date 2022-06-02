package com.don.usersservice.service;

import com.don.usersservice.model.RefreshToken;
import com.don.usersservice.model.User;

/**
 * @author Donald Veizi
 */
public interface RefreshTokenService {

    RefreshToken getByToken(final String token);

    void verifyRefreshTokenExpiration(final RefreshToken refreshToken);

    RefreshToken createRefreshToken(final Long userId);

    void expireRefreshToken(RefreshToken refreshToken);

    long deleteTokens(final User user);
}
