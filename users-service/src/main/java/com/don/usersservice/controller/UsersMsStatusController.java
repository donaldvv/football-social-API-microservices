package com.don.usersservice.controller;

import com.don.usersservice.security.RequestPrincipalContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author Donald Veizi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersMsStatusController {

    @Resource(name = "requestPrincipalContext")
    private RequestPrincipalContext requestPrincipalContext;

    @PreAuthorize("hasRole('RECRUITER') or hasRole('USER')")
    @GetMapping("/status/check")
    public String getStatus() {
        System.out.println(requestPrincipalContext.getUserEmail());
        System.out.println(requestPrincipalContext.getUserId());
        return "Users Microservice is up and working :) " + requestPrincipalContext.getUserId();
    }

}
