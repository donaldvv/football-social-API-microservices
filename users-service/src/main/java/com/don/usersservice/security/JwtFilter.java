package com.don.usersservice.security;

import com.don.usersservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Donald Veizi
 *
 * The purpose of the class is to build the authentication object and set the corresponding user as authenticated in the
 * Spring Context. After the request has passed the Gateway, where the jwt has been validated, each of the microservices will
 * get the JWT from the request header, extract the necessary data (including the ROLES) and set the user as authenticated.
 * This way we can then determine behaviour based on roles of the authenticated principal, which we built using only the JWT token.
 */
@Component
@RequiredArgsConstructor
@Slf4j @Getter
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;

    private long requesterUserId;
    private String requesterUsername;

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

        Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);

        Map<String, String> usernameAndIdMap = getUsernameAndId(claims.getSubject());
        long userId = Long.parseLong(usernameAndIdMap.get("userId"));
        String username = usernameAndIdMap.get("username");
        requesterUserId = userId;
        requesterUsername = username;

        User principal = new User(username, "", authorities);

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

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        List<String> scopes = (List<String>) claims.get("scope");
        return scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Map<String, String> getUsernameAndId(String subject) {
        // format will be: "USER_ID=72, USERNAME=donald@gmail.com"
        final Map<String, String> userIdentifiersMap = new HashMap<>();
        final String userIdKey = "USER_ID=";
        final String usernameKey = "USERNAME=";

        final String[] splitSubject = subject.split(", ");
        final String idPart = splitSubject[0];
        final String userIDStr = idPart.replace(userIdKey, "");
        userIdentifiersMap.put("userId", userIDStr);

        String usernamePart = splitSubject[1];
        String username = usernamePart.replace(usernameKey, "");
        userIdentifiersMap.put("username", username);

        return userIdentifiersMap;
    }
}
