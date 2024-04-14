package com.aurora.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author panda00hi
 * @date 2024.04.07
 */
@RestController
public class DemoController {

    @GetMapping("/test")
    public Mono<String> test() {
        System.out.println("hello world...");
        return Mono.just("hello world");
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        System.out.println("fallback...");
        return Mono.just("fallback");
    }
}
