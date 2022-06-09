package com.don.postsservice.service.impl;

import com.don.postsservice.event.dto.Message;
import com.don.postsservice.event.dto.user.UserMessage;
import com.don.postsservice.exception.EntityNotFoundException;
import com.don.postsservice.exception.entity.type.EEntity;
import com.don.postsservice.mapper.UserExtMapper;
import com.don.postsservice.model.UserExt;
import com.don.postsservice.model.enums.EAction;
import com.don.postsservice.repository.UserExtRepository;
import com.don.postsservice.security.RequestPrincipalContext;
import com.don.postsservice.service.UserExtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserExtServiceImpl implements UserExtService {

    private final UserExtRepository userExtRepository;
    private final RequestPrincipalContext requestPrincipalContext;
    private final UserExtMapper userExtMapper;

    @Override
    public void processUserEvent(final Message<UserMessage> message) {
        final EAction action = message.getAction();
        log.info("USER ACTION = {}", action.name());

        final UserMessage userMessage = message.getEntity();
        switch (action) {
            case CREATE, UPDATE -> createOrUpdate(userMessage, action);
            case DELETE -> delete(userMessage);
            default -> log.error("ACTION INVALID");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserExt getLoggedUser() {
        long userId = requestPrincipalContext.getUserId();
        return userExtRepository.findByUserIdExt(userId)
                .orElseThrow(() -> {
                    log.error("User with the credentials of the authenticated user was not found in the database. User id: {}", userId);
                    throw new EntityNotFoundException(EEntity.USER, userId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserExt getUser(final Long userId) {
        return userExtRepository.findByUserIdExt(userId)
                .orElseThrow(() -> {
                    log.error("User with id: {} does not exist", userId);
                    throw new EntityNotFoundException(EEntity.USER, userId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyUserExists(final Long userId) {
        boolean exists = userExtRepository.existsById(userId);
        if (!exists) {
            log.error("User with id: {} does not exist", userId);
            throw new EntityNotFoundException(EEntity.USER, userId);
        }
    }

    @Override
    public Long getLoggedUserId() {
        return requestPrincipalContext.getUserId();
    }

    // provides only the reference object which contains only ID. Gets used very rarely, mostly in cases where the entity already exists
    // and we just want to use it to set a relationship with another entity (to set the FK)
    @Override
    public UserExt getUserReference(final Long userId) {
        return userExtRepository.getById(userId);
    }


    private void createOrUpdate(final UserMessage user, final EAction action) {
        if (action.equals(EAction.UPDATE)) {
            Optional<UserExt> dbUserExt = userExtRepository.findByUserIdExt(user.getUserId());
            if (dbUserExt.isEmpty()) {
                log.error("UserExt with userIdExt: {}, was not found!", user.getUserId());
            } else {
                updateUserExt(dbUserExt.get(), user);
            }
        } else {
            createUserExt(user);
        }
    }

    private void delete(final UserMessage user) {
        userExtRepository.deleteById(user.getUserId());
    }

    private void updateUserExt(final UserExt dbUserExt, final UserMessage user) {
        UserExt userExt = userExtMapper.userMessageToUserExt(user);
        userExt.setId(dbUserExt.getId());
        userExtRepository.save(userExt);
    }

    private void createUserExt(final UserMessage user) {
        final UserExt userExt = userExtMapper.userMessageToUserExt(user);
        userExtRepository.save(userExt);
    }

}
