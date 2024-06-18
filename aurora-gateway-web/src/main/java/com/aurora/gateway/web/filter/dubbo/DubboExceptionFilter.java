package com.aurora.gateway.web.filter.dubbo;

import com.aurora.gateway.common.model.base.BaseException;
import com.aurora.gateway.common.model.rsp.CommonResult;
import com.aurora.gateway.core.base.ErrorCode;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.BaseFilter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;
import java.util.Optional;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * 统一处理dubbo异常
 *
 * @author panda00hi
 * @date 2023.12.01
 */
@Slf4j
@Activate(group = PROVIDER, order = -2000)
public class DubboExceptionFilter extends DubboBaseFilter implements BaseFilter.Listener {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }


    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
            try {
                Throwable exception = appResponse.getException();
                Optional<CommonResult> response = createResponse(Optional.of(appResponse), invoker, invocation);
                if (response.isEmpty()) {
                    throw new RpcException("appResponseException", exception);
                }
                CommonResult commonResult = response.get();
                log.error("onResponse {}", exception.getMessage(), exception);
                // 无需抛出异常，而是给出统一的业务结果对象
                appResponse.setException(null);

                if (exception instanceof ValidationException) {
                    commonResult.setCode(ErrorCode.REQUEST_PARAM_INVALID.code());
                    commonResult.setMessage(exception.getMessage());
                } else if (BaseException.class.isAssignableFrom(exception.getClass())) {
                    BaseException serviceException = (BaseException) exception;
                    commonResult.setCode(serviceException.getCode());
                    commonResult.setMessage(serviceException.getMessage());
                } else {
                    commonResult.setErrorCode(ErrorCode.SYSTEM_ERROR);
                }
                appResponse.setValue(commonResult);
            } catch (RpcException e) {
                throw e;
            } catch (Throwable e) {
                log.error("onResponse Fail to DubboExceptionFilter when called by " + RpcContext.getContext()
                        .getRemoteHost() + ". service: " + invoker.getInterface().getName()
                        + ", method: " + invocation.getMethodName() + ", exception: " + e.getClass()
                        .getName() + ": " + e.getMessage(), e);
                throw new RpcException("appResponseException Throwable", e);
            }
        }
    }

    @Override
    public void onError(Throwable e, Invoker<?> invoker, Invocation invocation) {
    }

}