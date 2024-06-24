package com.aurora.gateway.web.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panda00hi
 * @date 2024.06.22
 */
@Configuration
@EnableDubbo
public class DubboConfig {

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("gateway-service");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("nacos://172.16.224.100:8848");
        registryConfig.setCheck(false);
        return registryConfig;
    }

    @Bean
    public ReferenceConfig<GenericService> referenceConfig(){
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.aurora.dubbo.api.AdminFacade");
        referenceConfig.setApplication(applicationConfig());
        referenceConfig.setRegistry(registryConfig());

        //重点：设置为泛化调用
        //注：不再推荐使用参数为布尔值的setGeneric函数
        //应该使用referenceConfig.setGeneric("true")代替
        referenceConfig.setGeneric("true");
        //设置超时时间
        referenceConfig.setTimeout(20000);
        return referenceConfig;
    }
}
