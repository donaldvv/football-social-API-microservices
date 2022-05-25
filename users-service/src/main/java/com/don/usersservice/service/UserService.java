package com.don.usersservice.service;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.UserRegisterRequest;
import com.don.usersservice.model.User;

public interface UserService {

    UserDTO register(UserRegisterRequest registerRequest);

    User getUserById(Long userId);

    UserDTO getUserByEmail(String email);
}
