package com.don.postsservice.service.impl;

import com.don.postsservice.event.dto.Message;
import com.don.postsservice.event.dto.user.UserMessage;
import com.don.postsservice.mapper.UserExtMapper;
import com.don.postsservice.model.UserExt;
import com.don.postsservice.model.enums.EAction;
import com.don.postsservice.repository.UserExtRepository;
import com.don.postsservice.service.UserExtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserExtServiceImpl implements UserExtService {

    private final UserExtRepository userExtRepository;
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
