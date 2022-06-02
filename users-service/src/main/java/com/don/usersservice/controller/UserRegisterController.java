package com.don.usersservice.controller;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.UserRegisterRequest;
import com.don.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Donald Veizi
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegisterRequest registerRequest) {
        UserDTO createdUser = userService.register(registerRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
