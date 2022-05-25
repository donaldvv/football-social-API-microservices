package com.don.usersservice.service.impl;

import com.don.usersservice.exception.TokenRefreshException;
import com.don.usersservice.model.RefreshToken;
import com.don.usersservice.model.User;
import com.don.usersservice.repository.RefreshTokenRepository;
import com.don.usersservice.service.RefreshTokenService;
import com.don.usersservice.service.UserService;
import com.don.usersservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtUtils jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    @Lazy
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public RefreshToken getByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Refresh Token not found in the Database");
                    throw new TokenRefreshException(token, "Refresh Token not found in the Database");
                });
    }

    @Override
    public void verifyRefreshTokenExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiration().compareTo(Instant.now()) < 0) {
            log.error(String.format("Refresh Token: %s has expired", refreshToken.getToken()));
            refreshTokenRepository.delete(refreshToken);
            log.debug("Expired Refresh Token was deleted from the Database");
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh Token has expired");
        }
    }


    @Override
    public RefreshToken createRefreshToken(Long userId) {
        Long refreshTokenDuration = jwtUtil.getRefreshTokenDuration();
        User user = getUser(userId);
        RefreshToken refreshTokenToSave = new RefreshToken(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(refreshTokenDuration)
        );
        return refreshTokenRepository.save(refreshTokenToSave);
    }

    @Override
    public void expireRefreshToken(RefreshToken refreshToken) {
        refreshToken.setExpiration(Instant.now());
        refreshTokenRepository.save(refreshToken);
    }

    private User getUser(Long userId) {
        return userService.getUserById(userId);
    }
}