package com.aurora.gateway.web.filter.http;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 为所有的请求添加 traceId
 *
 * @author panda00hi
 * @date 2024.06.19
 */
@Slf4j
@Component
public class TraceIdFilter implements GlobalFilter, Ordered {
    /**
     * 统一 traceId key
     */
    private final String traceId = "traceId";

    @Override
    public int getOrder() {
        // 优先级最高
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String oldTraceId = exchange.getRequest().getHeaders().getFirst(traceId);
        if (null != oldTraceId && oldTraceId.length() > 0) {
            MDC.put(traceId, oldTraceId);
        } else {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            MDC.put(traceId, uuid);
            exchange.getRequest()
                    .mutate()
                    .headers(httpHeaders -> httpHeaders.add(traceId, uuid));
        }
        return chain.filter(exchange);
    }

}


