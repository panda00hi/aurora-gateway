package com.aurora.gateway.web.filter.dubbo;

import com.aurora.gateway.common.model.rsp.CommonResult;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.support.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;

import static org.apache.dubbo.common.constants.CommonConstants.$INVOKE;
import static org.apache.dubbo.rpc.Constants.INVOCATION_KEY;

/**
 * BaseFilter
 *
 * @author panda00hi
 * @date 2023.12.01
 */
public abstract class DubboBaseFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboBaseFilter.class);

    public DubboBaseFilter() {
    }

    protected Optional<CommonResult> createResponse(Optional<Result> appResponse, Invoker<?> invoker, Invocation invocation)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class ret = null;
        if (invocation.getMethodName().equals($INVOKE) || invocation.getMethodName().equals(CommonConstants.$INVOKE_ASYNC)) {
            Type[] returnTypes = null;
            if (appResponse.isPresent()) {
                if (appResponse.get() instanceof AppResponse) {
                    Object invocationObj = ((AppResponse) appResponse.get()).getAttribute(INVOCATION_KEY);
                    if (invocationObj != null) {
                        if (invocationObj instanceof RpcInvocation) {
                            returnTypes = ((RpcInvocation) invocationObj).getReturnTypes();
                        } else {
                            returnTypes = RpcUtils.getReturnTypes((Invocation) invocationObj);
                        }
                    }
                }
            }
            if (returnTypes != null) {
                ret = (Class) returnTypes[0];
            }
            if (!CommonResult.class.isAssignableFrom(ret)) {
                String name = ((String) invocation.getArguments()[0]).trim();
                String[] types = (String[]) invocation.getArguments()[1];
                try {
                    Method method = ReflectUtils.findMethodByMethodSignature(invoker.getInterface(), name, types);
                    ret = method.getReturnType();
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    throw new RpcException(e.getMessage(), e);
                }
            }
        }
        if (ret == null) {
            Type[] returnTypes = null;
            if (invocation instanceof RpcInvocation) {
                returnTypes = ((RpcInvocation) invocation).getReturnTypes();
            } else {
                returnTypes = RpcUtils.getReturnTypes(invocation);
            }
            ret = (Class) returnTypes[0];
        }

        if (!CommonResult.class.isAssignableFrom(ret)) {
            return Optional.empty();
        }
        return Optional.of((CommonResult) ret.getDeclaredConstructor().newInstance());
    }
}
