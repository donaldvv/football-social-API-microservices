package com.don.usersservice.repository;

import com.don.usersservice.model.User;
import com.don.usersservice.model.UserClub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Donald Veizi
 */
public interface UserClubRepository extends JpaRepository<UserClub, Long> {

    long deleteByUser(User user);
}
