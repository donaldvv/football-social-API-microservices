package com.don.usersservice.service;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.UserRegisterRequest;
import com.don.usersservice.model.User;

/**
 * @author Donald Veizi
 */
public interface UserService {

    UserDTO register(UserRegisterRequest registerRequest);

    User getUserById(final Long userId);

    User getUserByEmail(final String email);

    UserDTO getUserProfile(final Long userId);

    void deleteAccount(final Long userId);
}