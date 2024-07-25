package com.aurora.gateway.web.filter;

import com.aurora.gateway.common.HeaderConstants;
import com.aurora.gateway.core.service.AuthService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 * @author panda00hi
 * @date 2024.06.19
 */
@Component
public class SignFilter implements GlobalFilter, Ordered {
    private static final String[] EXCLUDED_PATHS = {"/login", "/logout"};

    @Resource
    private AuthService authService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (isExcludedPath(request)) {
            return chain.filter(exchange);
        }

        // todo 获取请求头中的各个信息，并先校验时间戳、重放控制随机数、签名
        long timestamp = Long.parseLong(Optional.ofNullable(request.getHeaders().getFirst(HeaderConstants.ACCEPT_TIME)).orElse("0"));
        String accessKey = request.getHeaders().getFirst(HeaderConstants.ACCEPT_ACCESS_KEY);
        String sign = request.getHeaders().getFirst(HeaderConstants.ACCEPT_SIGN);

        // 若存在授权头，则进行token
        String authHeader = request.getHeaders().getFirst(HeaderConstants.TOKEN);
        if (!StringUtils.isNotBlank(authHeader)) {
            if (!authService.validateToken(authHeader)) {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return response.setComplete();
            }
        }
        // 若不存在授权头，则需要通过ak、签名鉴权
        // 校验过期的请求
        if (System.currentTimeMillis() - timestamp > Duration.ofMinutes(1).getSeconds() * 1000) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        // if (!authService.validateAccess(authHeader)) {
        //     response.setStatusCode(HttpStatus.FORBIDDEN);
        //     return response.setComplete();
        // }

        return chain.filter(exchange);
    }

    private boolean isExcludedPath(ServerHttpRequest request) {
        for (String path : EXCLUDED_PATHS) {
            if (request.getPath().value().startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 99; // 越小越先执行
    }
}
