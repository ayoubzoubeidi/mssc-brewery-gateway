package com.maz.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!local")
@Configuration
public class LocalHostRouteConfig {

    @Bean
    public RouteLocator localHostRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                //Beer Service Route
                .route(r -> r.path("/api/v1/beer*", "/api/v1/beer/upc*", "/api/v1/beer/upc/*")
                        .uri("http://localhost:8080"))

                //Beer Order Service Route
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081"))

                // Beer Inventory Service Route

                .route(r -> r.path("api/v1/beer/*/inventory")
                        .uri("http://localhost:8082"))
                .build();
    }


}
