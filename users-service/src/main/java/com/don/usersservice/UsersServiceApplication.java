package com.don.usersservice;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients   // necessary to use the @FeignClient
public class UsersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
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

}
