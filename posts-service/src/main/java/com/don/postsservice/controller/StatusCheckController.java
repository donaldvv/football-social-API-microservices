package com.don.postsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Donald Veizi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class StatusCheckController {

        @PreAuthorize("hasRole('RECRUITER') or hasRole('USER')")
        @GetMapping("/status/check")
        public String getStatus() {
            return "Posts Microservice is up and running :D ";
        }
}
