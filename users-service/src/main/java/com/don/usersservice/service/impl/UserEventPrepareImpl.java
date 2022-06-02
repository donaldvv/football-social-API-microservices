package com.don.usersservice.service.impl;

import com.don.usersservice.event.UserEventProducer;
import com.don.usersservice.event.dto.Message;
import com.don.usersservice.event.dto.user.UserMessage;
import com.don.usersservice.mapper.UserMapper;
import com.don.usersservice.model.User;
import com.don.usersservice.model.enums.EAction;
import com.don.usersservice.service.UserEventPrepare;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor @Slf4j
public class UserEventPrepareImpl implements UserEventPrepare {

    private final UserMapper userMapper;
    private final UserEventProducer userEventProducer;

    @Async
    @Override // pse pa Override kto ??????????????
    public void produceUserCreatedEvent(final User user) {
        Message<UserMessage> message = new Message<>();
        final UserMessage userMessage = userMapper.userToUserMessage(user);
        message.setEntity(userMessage);
        message.setAction(EAction.CREATE);
        sendUserEventMessage(message);
    }

    // TODO: UserEditEvent -> after adding edit profile functionality

    @Async
    public void produceUserDeletedEvent(final Long userId) { // the consumer should also delete the posts and comments ... for this user
        Message<UserMessage> message = new Message<>();
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(userId);
        message.setEntity(userMessage);
        message.setAction(EAction.DELETE);
        sendUserEventMessage(message);
    }

    private void sendUserEventMessage(final Message<UserMessage> message) {
        log.info("SENDING USER WRITE EVENT MESSAGE FROM USER-MS");
        userEventProducer.sendUserWrite(message);
    }

}