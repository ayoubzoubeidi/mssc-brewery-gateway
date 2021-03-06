package com.maz.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Profile("test-google-search")
//@Configuration
public class GoogleConfig {

    @Bean
    public RouteLocator googleRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/search")
                        .filters(f -> f.rewritePath("/search(?<segment>/?.*)", "/${segment}"))
                        .uri("https://google.com")
                )
                .build();
    }

}
