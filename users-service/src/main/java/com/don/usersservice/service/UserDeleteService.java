package com.don.usersservice.service;

import com.don.usersservice.model.User;

/**
 * @author Donald Veizi
 */
public interface UserDeleteService {

    /**
     * Deletes the user and the related data whose lifespan is dependent on the user
     * @param user {@link User}
     */
    void deleteUserAndRelated(final User user);
}
