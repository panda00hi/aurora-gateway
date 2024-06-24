package com.aurora.gateway.core.utils;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;

/**
 * dubbo泛化调用实现类
 *
 * @author panda00hi
 * @date 2024.06.24
 */
@Component
public class DubboGenericService {

    private final ReferenceConfig<GenericService> referenceConfig;

    public DubboGenericService(ReferenceConfig<GenericService> genericServiceRef) {
        this.referenceConfig = genericServiceRef;
    }

    // @Resource
    // private ReferenceConfig<GenericService> referenceConfig;


    /**
     * 使用GenericService类对象的$invoke方法可以代替原方法使用
     * 参考 https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/advanced-features-and-usage/service/generic-reference/
     *
     * @param interfaceName  暴露的dubbo接口全类名
     * @param methodName     参数是需要调用的方法名
     * @param parameterTypes 需要调用的方法的参数类型数组，为String数组，里面存入参数的全类名
     * @param args           需要调用的方法的参数数组，为Object数组，里面存入需要的参数
     * @return 调用结果
     */
    public Object invoke(String interfaceName, String methodName, String[] parameterTypes, Object[] args) {

        referenceConfig.setInterface(interfaceName);
        // 获取服务，由于是泛化调用，所以获取的一定是GenericService类型
        GenericService genericService = referenceConfig.get();
        Object result = genericService.$invoke(methodName, parameterTypes, args);
        return result;

    }

}
