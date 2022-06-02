package com.don.usersservice.controller;

import com.don.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @author Donald Veizi
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class AccountDeleteController {

    private final UserService userService;

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('USER')")
    public ResponseEntity<String> deleteAccount(@Min(1) @PathVariable(value = "userId") Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.ok("Deleted user successfully");
    }

}
