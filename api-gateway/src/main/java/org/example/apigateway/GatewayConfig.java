package org.example.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(predicateSpec ->
                        predicateSpec
                                .path("/api/v1/users/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.addRequestHeader("src", "hello product service....")
                                )
                                .uri("http://localhost:8081/")
                )
                .route(predicateSpec ->
                        predicateSpec
                                .path("/api/v1/products/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.addRequestHeader("src", "hello user service....")
                                )
                                .uri("http://localhost:8082/")
                )
                .route(predicateSpec ->
                        predicateSpec
                                .path("/api/v1/carts/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.addRequestHeader("src", "hello user service....")
                                )
                                .uri("http://localhost:8083/")
                )
                .route(predicateSpec ->
                        predicateSpec
                                .path("/api/v1/payments/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.addRequestHeader("src", "hello user service....")
                                )
                                .uri("http://localhost:8084/")
                )
                .build();
    }
}
