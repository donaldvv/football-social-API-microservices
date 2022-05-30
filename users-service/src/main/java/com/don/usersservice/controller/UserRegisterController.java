package com.don.usersservice.controller;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.UserRegisterRequest;
import com.don.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Donald Veizi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegisterRequest registerRequest) {
        UserDTO createdUser = userService.register(registerRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
