package com.nishikant.api_gateway.config;


import com.nishikant.api_gateway.filter.JwtAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, JwtAuthFilter jwtAuthFilter) {
        return builder.routes()
                .route("user-service", r -> r.path("/user/**")
                        .filters(f -> f.filter((exchange, chain) -> jwtAuthFilter.filter(exchange, chain)))
                        .uri("lb://user-service"))

                .route("task-service", r -> r.path("/tasks/**")
                        .filters(f -> f.filter((exchange, chain) -> jwtAuthFilter.filter(exchange, chain)))
                        .uri("lb://task-service"))

                .build();
    }
}
