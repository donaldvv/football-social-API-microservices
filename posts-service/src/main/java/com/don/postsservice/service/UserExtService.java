package com.don.postsservice.service;

import com.don.postsservice.event.dto.Message;
import com.don.postsservice.event.dto.user.UserMessage;

/**
 * @author Donald Veizi
 */
public interface UserExtService {

    /**
     * Handles the user related event. Will create/update/delete user data based on the action specified in the message.
     * For simplicity, we assume that the message is always valid (normally we would always need to do validation)
     * @param message the message received from the queue that listens to user related messages
     */
    void processUserEvent(Message<UserMessage> message);
}
