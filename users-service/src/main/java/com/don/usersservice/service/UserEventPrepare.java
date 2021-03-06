package com.don.usersservice.service;

import com.don.usersservice.model.User;

/**
 * @author Donald Veizi
 */
public interface UserEventPrepare {

    /**
     * Will produce the User creation event, in non-blocking way.
     * @param user User Entity
     */
    void produceUserCreatedEvent(final User user);

    /**
     * Will produce the User deletion event, in non-blocking way.
     * @param userId id of the user
     */
    void produceUserDeletedEvent(final Long userId);
}
