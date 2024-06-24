package com.aurora.gateway.core.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aurora.gateway.common.model.auth.AccessDTO;
import com.aurora.gateway.core.service.AuthService;
import com.aurora.gateway.core.utils.DubboGenericService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

/**
 * @author panda00hi
 * @date 2024.06.19
 */
@Slf4j
@Component
public class AuthServiceImpl implements AuthService {

    @Resource
    private DubboGenericService dubboGenericService;


    @Override
    public AccessDTO getAccessInfo(String ak) {
        // 以json方式接收
        Object obj = dubboGenericService.invoke("com.aurora.dubbo.api.AdminFacade",
                "getAccessInfo", new String[]{"java.lang.String"},
                new Object[]{ak});
        // 转为AccessDTO
        return JSON.parseObject(JSON.toJSONString(obj), AccessDTO.class);
    }

    public boolean validateAccess(String ak, String sk) {
        String interfaceName = "com.aurora.dubbo.api.AdminFacade";
        String methodName = "validateAKSK";
        String[] parameterTypes = new String[]{"java.lang.String", "java.lang.String"};
        Object[] args = new Object[]{ak, sk};
        Object result = dubboGenericService.invoke(interfaceName, methodName, parameterTypes, args);
        return Boolean.parseBoolean(result.toString());
    }

    public boolean validateToken(String token) {
        String interfaceName = "com.aurora.dubbo.api.AdminFacade";
        String methodName = "validateToken";
        String[] parameterTypes = new String[]{"java.lang.String"};
        Object[] args = new Object[]{token};
        Object result = dubboGenericService.invoke(interfaceName, methodName, parameterTypes, args);
        return Boolean.parseBoolean(result.toString());
    }
}
