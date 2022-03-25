package com.dronworks.apigateway.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/get") // can be matched on multiple parameters!
                .filters(f -> f.addRequestHeader("MyHeader", "MyUri")
                        .addRequestParameter("SomeParam", "Param"))
                .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://CURRENCY-EXCHANGE")) // Load Balance - gets from EUREKA
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://CURRENCY-CONVERSION")) // Load Balance - gets from EUREKA
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://CURRENCY-CONVERSION")) // Load Balance - gets from EUREKA
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}")) // Rewrite path with the segment that comes after
                        .uri("lb://CURRENCY-CONVERSION"))
                .build();
    }

}
