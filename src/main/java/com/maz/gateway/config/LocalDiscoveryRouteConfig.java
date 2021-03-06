package com.maz.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
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

                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .filters(f ->
                                f.circuitBreaker(cb -> cb.setName("inventoryCircuitBreaker")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inventory-failover")))
                        .uri("lb://inventory-service"))

                // Inventory FailOver Route
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://inventory-failover-service"))
                .build();
    }

}
