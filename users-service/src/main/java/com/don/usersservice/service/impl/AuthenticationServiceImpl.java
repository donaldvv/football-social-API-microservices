package com.don.usersservice.service.impl;

import com.don.usersservice.dto.request.LoginRequest;
import com.don.usersservice.dto.request.RefreshTokenRequest;
import com.don.usersservice.dto.response.JwtResponse;
import com.don.usersservice.dto.response.RefreshTokenResponse;
import com.don.usersservice.model.RefreshToken;
import com.don.usersservice.model.Role;
import com.don.usersservice.model.User;
import com.don.usersservice.model.enums.ERole;
import com.don.usersservice.service.AuthenticationService;
import com.don.usersservice.service.RefreshTokenService;
import com.don.usersservice.service.UserService;
import com.don.usersservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final UserService userService;

    @Override
    public JwtResponse authenticateUser(final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        // manually set this user as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userService.getUserByEmail(loginRequest.getEmail());
        final String jwtToken = jwtUtil.generateTokenFromDBUser(user);
        final Collection<String> userRoles = getRolesFromUser(user);
        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new JwtResponse(
                jwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getEmail(),
                userRoles);
    }

    @Override
    public RefreshTokenResponse getRefreshedAccessToken(final RefreshTokenRequest request) {
        final String requestTokenRefresh = request.getRefreshToken();
        // get the RefreshToken Object in the database using the provided Refresh Token String by the client
        final RefreshToken tokenInTheDb = refreshTokenService.getByToken(requestTokenRefresh);
        refreshTokenService.verifyRefreshTokenExpiration(tokenInTheDb);
        final User user = tokenInTheDb.getUser();
        final String jwtAccessToken = jwtUtil.generateTokenFromDBUser(user);

        return new RefreshTokenResponse(jwtAccessToken, requestTokenRefresh);
    }

    // another option would be to make this method transactional, and after getting the RefreshToken, we set expiration,
    // and since the object would be in a managed state inside the transaction, the changes would be reflected in the DB as well
    @Override
    public String expireRefreshToken(final RefreshTokenRequest request) {
        final String requestTokenRefresh = request.getRefreshToken();
        final RefreshToken tokenInTheDb = refreshTokenService.getByToken(requestTokenRefresh);
        refreshTokenService.expireRefreshToken(tokenInTheDb);
        return "Successfully expired the refresh token!";
    }

/*    private Collection<String> getRolesFromUserDetails(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }*/

    private List<String> getRolesFromUser(final User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .map(ERole::name)
                .toList();
    }

}
