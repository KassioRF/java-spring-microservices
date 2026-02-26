package com.acme.tickets.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayApiConfig {

    @Value("${gateway.webapp.uri}")
    private String webAppUri;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users-api", r -> r.path("/api/users/**")
                        .filters(f -> f.rewritePath("/api/users/?(?<segment>.*)", "/${segment}"))
                        .uri("lb://users-service"))

                .route("sales-api", r -> r.path("/api/sales/**")
                        .filters(f -> f.rewritePath("/api/sales/?(?<segment>.*)", "/${segment}"))
                        .uri("lb://sales-service"))

                .route("webapp",
                        pred -> pred.path("/**")
                                .uri(getWebAppURI()))

                .build();
    }

    private String getWebAppURI() {
        if (this.webAppUri == null) {
            throw new RuntimeException("WebAPP URI is empty");
        }
        return this.webAppUri;
    }

}
