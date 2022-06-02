package com.don.usersservice.service.impl;

import com.don.usersservice.model.User;
import com.don.usersservice.repository.UserClubRepository;
import com.don.usersservice.repository.UserRepository;
import com.don.usersservice.service.RefreshTokenService;
import com.don.usersservice.service.UserDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeleteServiceImpl implements UserDeleteService {

    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    // TODO: maybe have a UserClubService here, if we will have other UserClub related methods
    private final UserClubRepository userClubRepository;

    @Override
    public void deleteUserAndRelated(final User user) {
        refreshTokenService.deleteTokens(user);
        log.info("Deleted refresh tokens of user");

        userClubRepository.deleteByUser(user);
        log.info("Deleted football clubs that user has worked on");

        userRepository.delete(user);
        log.info("Deleted user");
    }

}
