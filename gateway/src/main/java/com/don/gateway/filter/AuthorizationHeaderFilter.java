package com.don.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component // AbstractGatewayFilterFactory<TakesAConfigClassHere>
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment env;

    // tells the parent class which Config Class to use, when calling the apply() method
    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    // applies the Configuration that are passed as generic argument to the AbstractGatewayFilterFactory
    @Override
    public GatewayFilter apply(Config config) {
        // *** ApiGateway requires Reactive Programming !!!


        // the exchange object can be used to get a hold of the request object, and then its header
        // the chain, is an objects which represent the chain of filters in the ApiGateway
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);

            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authHeader.replace("Bearer", "");

            if (!isJwtValid(jwt))
                return onError(exchange, "JWT token-not valid", HttpStatus.UNAUTHORIZED);

            return chain.filter(exchange); // invokes the next filter in the chain
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String no_authorization_header, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        boolean returnVal = true;
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody();

            subject = claims.getSubject(); // username

            returnVal = isNotExpired(claims);
        } catch (Exception e){
            returnVal = false;
        }

        if (subject == null || subject.isEmpty())
            returnVal = false;
        System.out.println("tek auth header filter");
        return returnVal;
    }

    private Boolean isNotExpired(Claims claims) {
        final Date expiration = getExpirationDateFromToken(claims);
        return !expiration.before(new Date()); //pra returns true nqs JO expired
    }

    private Date getExpirationDateFromToken(Claims claims) {
        return claims.getExpiration();
    }

    public static class Config {
        // put config properties here
    }
}
