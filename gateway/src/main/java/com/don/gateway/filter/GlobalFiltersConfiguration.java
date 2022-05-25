package com.don.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {

    final Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    // I can make a Pre-Filter, do both Pre & Post filtering logic
    @Bean
    @Order(1)
    public GlobalFilter secondPreFilter() {

        return (exchange, chain) -> {

            logger.info("My 2nd global pre-filter is executed!");

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> logger.info("My 2nd global post-filter is executed!")));
        };
    }

    @Bean
    @Order(2)
    public GlobalFilter thirdPreFilter() {

        return (exchange, chain) -> {

            logger.info("My 3rd global pre-filter is executed!");

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> logger.info("My 3rd global post-filter is executed!")));
        };
    }
}
