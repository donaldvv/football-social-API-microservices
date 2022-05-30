package com.don.usersservice.repository;

import com.don.usersservice.model.RefreshToken;
import com.don.usersservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Donald Veizi
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findById(Long refTokenId);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}

