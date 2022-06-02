package com.don.usersservice.controller;

import com.don.usersservice.security.RequestPrincipalContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author Donald Veizi
 */
@RestController
@RequiredArgsConstructor @Slf4j
@CrossOrigin
@RequestMapping("/users")
public class UsersMsStatusController {

    @Resource(name = "requestPrincipalContext")
    private RequestPrincipalContext requestPrincipalContext;

    @PreAuthorize("hasRole('RECRUITER') or hasRole('USER')")
    @GetMapping("/status/check")
    public String getStatus() {
        log.info(String.format("User id: %s", requestPrincipalContext.getUserEmail()));
        log.info(String.format("User id: %s", requestPrincipalContext.getUserId()));
        return "Users Microservice is up and working :) " + requestPrincipalContext.getUserId();
    }

}
