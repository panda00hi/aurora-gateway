package com.aurora.dubbo.api;

import com.aurora.dubbo.dto.AccessDTO;
import com.aurora.dubbo.dto.UserDTO;

/**
 * @author panda00hi
 * @date 2024.06.24
 */
public interface AdminFacade {
    String sayHello(String name);


    UserDTO getUserInfo(String token);

    AccessDTO getAccessInfo(String ak);

}
