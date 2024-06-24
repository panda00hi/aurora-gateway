package com.aurora.dubbo.api.impl;

import com.aurora.dubbo.api.AdminFacade;
import com.aurora.dubbo.model.AccessDTO;
import com.aurora.dubbo.model.UserDTO;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author panda00hi
 * @date 2024.06.24
 */
@DubboService()
public class AdminFacadeImpl implements AdminFacade {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }


    @Override
    public UserDTO getUserInfo(String token) {
        return new UserDTO();
    }

    @Override
    public AccessDTO getAccessInfo(String ak) {
        // 实际通过数据库生成
        return buildAccessInfo();
    }

    // 构建AccessDTO
    private AccessDTO buildAccessInfo() {
        return AccessDTO.builder().id(1)
                .status(0)
                .expireTime(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
                .secretKey("SK_xxx")
                .tenantName("tenant")
                .accessKey("AK_xxx")
                .build();

    }
}
