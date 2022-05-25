package com.don.usersservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersMsStatusController {

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/status/check")
    public String getStatus() {
        return "Users Microservice is up and working :) ";
    }

}
