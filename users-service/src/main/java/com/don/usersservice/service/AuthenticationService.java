package com.don.usersservice.service;

import com.don.usersservice.dto.request.LoginRequest;
import com.don.usersservice.dto.request.RefreshTokenRequest;
import com.don.usersservice.dto.response.JwtResponse;
import com.don.usersservice.dto.response.RefreshTokenResponse;


public interface AuthenticationService {

    JwtResponse authenticateUser(LoginRequest loginRequest);

    RefreshTokenResponse getRefreshedAccessToken(RefreshTokenRequest request);

    String expireRefreshToken(RefreshTokenRequest request);
}
