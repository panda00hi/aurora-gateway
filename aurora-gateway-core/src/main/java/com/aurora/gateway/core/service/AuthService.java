package com.aurora.gateway.core.service;

import com.aurora.gateway.common.model.auth.AccessDTO;

/**
 * @author panda00hi
 * @date 2024.06.19
 */
public interface AuthService {

    boolean validateAccess(String ak, String sk);

    boolean validateToken(String token);

    // 获取租户信息
    AccessDTO getAccessInfo(String ak);
}
