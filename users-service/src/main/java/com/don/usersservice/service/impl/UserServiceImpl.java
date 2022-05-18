package com.don.usersservice.service.impl;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.UserRegisterRequest;
import com.don.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    @Override
    public UserDTO register(UserRegisterRequest registerRequest) {
        return null;
    }
}
