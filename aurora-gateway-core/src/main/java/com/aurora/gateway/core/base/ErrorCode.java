package com.aurora.gateway.core.base;

import com.aurora.gateway.common.model.base.BaseErrorCode;
import com.aurora.gateway.common.model.base.BaseErrorCodeClass;
import com.aurora.gateway.common.model.rsp.CommonResult;

/**
 *
 */
public class ErrorCode extends BaseErrorCode {

    // code范围2001-2100，后50为自定义的业务code 2051-2100
    public final static BaseErrorCodeClass CODE_END = new BaseErrorCodeClass(2100, "");
    public static final BaseErrorCodeClass UNAUTHORIZED = new BaseErrorCodeClass(401, "暂未登录或token已经过期");
    public final static BaseErrorCodeClass FORBIDDEN = new BaseErrorCodeClass(403, "没有相关权限");


    // 自定义系统code，2001-2050
    public final static BaseErrorCodeClass REQUEST_PARAM_INVALID = new BaseErrorCodeClass(2000 + 1, "param invalid");
    public final static BaseErrorCodeClass REQUEST_PARAM_MISS = new BaseErrorCodeClass(2000 + 2, "param miss");
    public final static BaseErrorCodeClass SYSTEM_ERROR = new BaseErrorCodeClass(2000 + 3, "system error");
    public final static BaseErrorCodeClass RPC_REQUEST_ERROR = new BaseErrorCodeClass(2000 + 5, "rpc request error");

    // 自定义业务code，2051-2100


    public static CommonResult<String> createResult(BaseErrorCodeClass errorCodeClass, StringBuilder stringBuilder) {
        CommonResult<String> em = new CommonResult<>(errorCodeClass);
        if (stringBuilder != null) {
            if (stringBuilder.toString().length() > 0) {
                em.setMessage(stringBuilder.substring(0, stringBuilder.toString().length() - 1));
            }
        }
        return em;
    }

    public static CommonResult<String> createResult(BaseErrorCodeClass errorCodeClass, String str) {
        CommonResult<String> em = new CommonResult<>(errorCodeClass);
        if (str != null) {
            em.setMessage(str.substring(0, str.length() - 1));
        }
        return em;
    }
}
