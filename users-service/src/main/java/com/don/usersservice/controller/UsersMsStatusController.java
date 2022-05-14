package com.don.usersservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
public class UsersMsStatusController {

    @GetMapping("/status")
    public String getStatus() {
        return "Users Microservice is up and working :) ";
    }

}
