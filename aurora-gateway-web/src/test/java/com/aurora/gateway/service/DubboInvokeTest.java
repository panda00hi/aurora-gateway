package com.aurora.gateway.service;

import com.aurora.gateway.common.SpringBaseTest;
import com.aurora.gateway.core.utils.DubboGenericService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author panda00hi
 * @date 2024.06.24
 */
@Slf4j
class DubboInvokeTest extends SpringBaseTest {


    @Resource
    private DubboGenericService dubboGenericService;


    @Test
    void invoke() {
        String interfaceName = "com.aurora.dubbo.api.AdminFacade";
        String methodName = "sayHello";
        String[] parameterTypes = new String[]{"java.lang.String"};
        Object[] args = new Object[]{"world"};
        Object result = dubboGenericService.invoke(interfaceName, methodName, parameterTypes, args);
        log.info("invoke result:{}", result);
    }
}