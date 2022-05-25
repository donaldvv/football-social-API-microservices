package com.don.usersservice.controller;

import com.don.usersservice.dto.request.LoginRequest;
import com.don.usersservice.dto.request.RefreshTokenRequest;
import com.don.usersservice.dto.response.JwtResponse;
import com.don.usersservice.dto.response.RefreshTokenResponse;
import com.don.usersservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest logoutRequest) {
        String message = authenticationService.expireRefreshToken(logoutRequest);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> getRefreshedAccessToken(@Valid @RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse tokenRefreshResponse = authenticationService.getRefreshedAccessToken(request);
        return ResponseEntity.ok(tokenRefreshResponse);
    }
}
