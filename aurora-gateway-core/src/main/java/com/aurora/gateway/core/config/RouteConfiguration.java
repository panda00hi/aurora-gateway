package com.aurora.gateway.core.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panda00hi
 * @date 2024.04.07
 */
@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // 访问登录接口api/v1/login，进行校验，如果通过，则将返回信息封装成json返回
                // 如果不通过，则返回错误信息
                // 访问其他接口，直接转发到httpbin.org
                .route(p -> p
                        .path("/api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .build();
    }
}
