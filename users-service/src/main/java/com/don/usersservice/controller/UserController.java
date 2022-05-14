package com.don.usersservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@Validated
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
public class UserController {

    @GetMapping("/status")
    public String getStatus() {
        return "AAAAAAAAAAAAAAAAAA";
    }

}
