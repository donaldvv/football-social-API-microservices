package com.don.usersservice.service;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.UserRegisterRequest;

public interface UserService {

    UserDTO register(UserRegisterRequest registerRequest);
}
