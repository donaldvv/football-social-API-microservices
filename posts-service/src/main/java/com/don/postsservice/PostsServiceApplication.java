package com.don.postsservice;

import com.don.postsservice.security.JwtFilter;
import com.don.postsservice.security.RequestPrincipalContext;
import feign.Logger;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients   // necessary to use the @FeignClient
@OpenAPIDefinition(info =
    @Info(title = "Posts API", version = "1.0", description = "Documentation Posts API v1.0")
)
public class PostsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostsServiceApplication.class, args);
    }

    // besides the config in the app.properties file, in order to do logging for Feign Clients, we also need to set the level here as a Bean
    @Bean
    @Profile("!production")
    Logger.Level feignDefaultLoggerLevel()
    {
        return Logger.Level.FULL;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @RequestScope
    // The request scope creates a bean instance for a single HTTP request. After the request has passed the filter
    // and has stored userId and username as fields, then the bean below can be created
    public RequestPrincipalContext requestPrincipalContext(final JwtFilter jwtFilter) {
        RequestPrincipalContext requestPrincipalContext = new RequestPrincipalContext();
        requestPrincipalContext.setUserId(jwtFilter.getRequesterUserId());
        requestPrincipalContext.setUserEmail(jwtFilter.getRequesterUsername());
        return requestPrincipalContext;
    }
}
