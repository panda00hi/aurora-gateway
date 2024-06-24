package com.aurora.gateway.service;

import com.aurora.gateway.common.SpringBaseTest;
import com.aurora.gateway.common.model.auth.AccessDTO;
import com.aurora.gateway.core.service.AuthService;
import com.aurora.gateway.core.utils.DubboGenericService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author panda00hi
 * @date 2024.06.24
 */
@Slf4j
class AuthServiceTest extends SpringBaseTest {


    @Resource
    private AuthService authService;


    @Test
    void authService() {

        AccessDTO accessDTO = authService.getAccessInfo("AK_xxx");
        log.info("result:{}", accessDTO);
    }
}