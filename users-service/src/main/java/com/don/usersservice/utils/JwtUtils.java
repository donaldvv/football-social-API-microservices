package com.don.usersservice.utils;

import com.don.usersservice.model.Role;
import com.don.usersservice.model.User;
import com.don.usersservice.model.enums.ERole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtUtils {

    @Value("${token.secret}")
    private String jwtSecret;

    @Value("${token.expiration_time}")
    private int jwtExpirationMs;

    @Value("${refresh_token.expiration_time}")
    private Long refreshTokenDuration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }// praktikisht ben: Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody(). getSubject()


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    // nqs username qe marr nga user dhe username qe marr nga token perputhen && token not expired => valid token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date()); //pra returns true nqs expired
    }

    // Nga Claim (payload i token, qe mban key-value pairs me useful data), marr kohen e Expiration te token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public String generateTokenFromUserDetails(UserDetails userDetails) {
        return tokenBuilderFromUserDetails(userDetails);
    }

    // useful when user will provide refresh token, in order to get a new access token
    public String generateTokenFromDBUser(User user) {
        return tokenBuilderFromUser(user);
    }

    public Long getRefreshTokenDuration() {
        return refreshTokenDuration;
    }

    private String tokenBuilderFromUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<String> roles = getRolesFromUserDetails(userDetails);
        return tokenBuilder(username, roles);
    }

    private String tokenBuilderFromUser(User user) {
        String username = user.getEmail();
        List<String> roles = getRolesFromUser(user);
        return tokenBuilder(username, roles);
    }

    private String tokenBuilder(String username,  List<String> roles) {
        // the Claims will contain the username and the string represatation of the Authorities (roles) and I will
        // use these values in the Filters of each microservice to set the Spring Security Principal and the spring security will
        // then have all the necessary info for the user (username and roles - will be able to use @PreAuthorize)
/*
        Claims claims = Jwts.claims();
        claims.put("scope", roles);
        claims.setSubject(username);
*/
        log.debug("Generating token");
        return Jwts.builder()
                .claim("scope", roles)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 1h
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private List<String> getRolesFromUser(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .map(ERole::name)
                .collect(Collectors.toList());
    }

    private List<String> getRolesFromUserDetails(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public String getSecret() {
        return jwtSecret;
    }
}
