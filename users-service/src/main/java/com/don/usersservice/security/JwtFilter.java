package com.don.usersservice.security;

import com.don.usersservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The purpose of the class is to build the authentication object and set the corresponding user as authenticated in the
 * Spring Context. After the request has passed the Gateway, where the jwt has been validated, each of the microservices will
 * get the JWT from the request header, extract the necessary data (including the ROLES) and set the user as authenticated.
 * This way we can then determine behaviour based on roles of the authenticated principal, which we built using only the JWT token.
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final UserDetailsService userDetailsService;

    // Every time we get requests we do this, assuming there is a token inside the header
    // Than we keep going with the request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwt = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwt = requestTokenHeader.substring(7);
        } else {
            log.error("JWT token does not start with Bearer");
        }

        createAuthentication(jwt).ifPresent(authentication ->
                SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }

    private Optional<Authentication> createAuthentication(String token) {
        // will be correct bcs it was already validated in the Gateway. the point is to extract the Claims
        Jws<Claims> jwsClaims = validateToken(token);
        if (jwsClaims == null) {
            return Optional.empty();
        }

        Claims claims = jwsClaims.getBody();

        List<String> scopes = (List<String>) claims.get("scope");
        Collection<? extends GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String subject = claims.getSubject();
        User principal = new User(subject, "", authorities);

        return Optional.of(new UsernamePasswordAuthenticationToken(principal, token, authorities));
    }

    private Jws<Claims> validateToken(String authToken) {
        try {
            return Jwts.parser().setSigningKey(jwtUtil.getSecret()).parseClaimsJws(authToken);
        } catch (Exception e) {
            log.error("Something went wrong during the parsing of the jwt token");
            return null;
        }
    }
}
