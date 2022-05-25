package com.don.usersservice.service.impl;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.LoginRequest;
import com.don.usersservice.dto.request.RefreshTokenRequest;
import com.don.usersservice.dto.response.JwtResponse;
import com.don.usersservice.dto.response.RefreshTokenResponse;
import com.don.usersservice.mapper.UserMapper;
import com.don.usersservice.model.RefreshToken;
import com.don.usersservice.model.User;
import com.don.usersservice.repository.RoleRepository;
import com.don.usersservice.repository.UserRepository;
import com.don.usersservice.service.AuthenticationService;
import com.don.usersservice.service.RefreshTokenService;
import com.don.usersservice.service.UserService;
import com.don.usersservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        // manually set this user as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwtToken = jwtUtil.generateTokenFromUserDetails(userDetails);

        Collection<String> userRoles = getRolesFromUserDetails(userDetails);
        UserDTO user = userService.getUserByEmail(loginRequest.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new JwtResponse(
                jwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getEmail(),
                userRoles);
    }


    @Override
    public RefreshTokenResponse getRefreshedAccessToken(RefreshTokenRequest request) {
        String requestTokenRefresh = request.getRefreshToken();
        // get the RefreshToken Object in the database using the provided Refresh Token String by the client
        RefreshToken tokenInTheDb = refreshTokenService.getByToken(requestTokenRefresh);
        refreshTokenService.verifyRefreshTokenExpiration(tokenInTheDb);
        User user = tokenInTheDb.getUser();
        String jwtAccessToken = jwtUtil.generateTokenFromDBUser(user);
        // return the new Access Token && the same Refresh Token
        return new RefreshTokenResponse(jwtAccessToken, requestTokenRefresh);
    }

    // another option would be to make this method transactional, and after getting the RefreshToken, we set expiration,
    // and since the object would be in a managed state inside the transaction, the changes would be reflected in the DB as well
    @Override
    public String expireRefreshToken(RefreshTokenRequest request) {
        String requestTokenRefresh = request.getRefreshToken();
        RefreshToken tokenInTheDb = refreshTokenService.getByToken(requestTokenRefresh);
        refreshTokenService.expireRefreshToken(tokenInTheDb);
        return "Successfully expired the refresh token!";
    }

    private Collection<String> getRolesFromUserDetails(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

}
