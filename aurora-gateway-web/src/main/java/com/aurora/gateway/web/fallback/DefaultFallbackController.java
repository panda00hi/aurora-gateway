package com.aurora.gateway.web.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认的降级控制器
 *
 * @author panda00hi
 * @date 2024.04.18
 */
@RestController
@RequestMapping("/fallback")
public class DefaultFallbackController {

    @GetMapping("/default")
    public Object defaultFallback() {
        return null;
    }

}
