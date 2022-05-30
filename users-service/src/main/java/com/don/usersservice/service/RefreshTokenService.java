package com.don.usersservice.service;

import com.don.usersservice.model.RefreshToken;

/**
 * @author Donald Veizi
 */
public interface RefreshTokenService {

    RefreshToken getByToken(String token);

    void verifyRefreshTokenExpiration(RefreshToken refreshToken);

    RefreshToken createRefreshToken(Long userId);

    void expireRefreshToken(RefreshToken refreshToken);
}
