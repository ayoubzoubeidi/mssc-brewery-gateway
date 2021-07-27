package com.maz.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LocalDiscoveryRouteConfig {

    @Bean
    public RouteLocator localDiscoveryRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                //Beer Service Route
                .route(r -> r.path("/api/v1/beer*", "/api/v1/beer/upc*", "/api/v1/beer/upc/*")
                        .uri("lb://beer-service"))

                //Beer Order Service Route
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://order-service"))

                // Beer Inventory Service Route

                .route(r -> r.path("api/v1/beer/*/inventory")
                        .uri("lb://inventory-service"))
                .build();
    }

}
