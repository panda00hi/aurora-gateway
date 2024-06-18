package com.aurora.gateway.web.filter.dubbo;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.PostConstruct;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.Optional;

/**
 * 统一记录入参和返回
 *
 * @author panda00hi
 * @date 2023.12.01
 */
@Activate(order = -1000)
public class RequestLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);

    private static Environment environment;

    @PostConstruct
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String filterPkg = Optional.ofNullable(environment.getProperty("dubbo.scan.base-packages")).orElse("com.hh");
        if (invoker.getInterface().getName().startsWith(filterPkg)) {
            // 拦截并处理请求
            return handleRequest(invoker, invocation);
        } else {
            // 如果不匹配，则直接通过该 Filter 并继续执行下一个 Filter 或目标服务
            return invoker.invoke(invocation);
        }
    }

    private Result handleRequest(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        try {
            // 记录请求参数
            LOGGER.info("Invoking service: {} method: {}, params: {}",
                    invoker.getInterface().getName(), invocation.getMethodName(), JSONObject.toJSONString(invocation.getArguments()));

            // 调用下一个 Filter 或者直接调用服务提供方
            Result result = invoker.invoke(invocation);

            // 记录响应结果
            LOGGER.info("Received response for service: {} method: {}, result: {}",
                    invoker.getInterface().getName(), invocation.getMethodName(), JSONObject.toJSONString(result.getValue()));
            return result;
        } finally {
            // 记录耗时
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            LOGGER.info("Invocation of service: {} method: {} took {} ms",
                    invoker.getInterface().getName(), invocation.getMethodName(), duration);
        }
    }
}
