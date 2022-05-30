package com.don.postsservice.repository;

import com.don.postsservice.model.UserExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Donald Veizi
 */
public interface UserExtRepository extends JpaRepository<UserExt, Long> {

    Optional<UserExt> findByUserIdExt(Long userIdExt);
}
