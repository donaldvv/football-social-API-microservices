package com.don.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// Ne pergjithesi sekuenca: prefilter -> routing tek microservice (ekzek logjika e service) -> post filter
// Pra, Request -> kalon tek lista e Pre-Filters -> shkon tek Microservice -> kthehet mbrapsht tek Gateway duke kaluar tek Post-Filters
@Component
public class MyPostFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(MyPostFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // pasi te kalohet tek filtri tjeter ne chain, ekzekutoj logjiken e ketij filtri
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Post Filter!!!");
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
