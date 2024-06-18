package com.aurora.gateway.core.base;


import com.aurora.gateway.common.model.base.BaseErrorCode;
import com.aurora.gateway.common.model.base.BaseErrorCodeClass;
import com.aurora.gateway.common.model.base.BaseException;

/**
 * 自定义业务异常
 */
public class BizException extends RuntimeException implements BaseException {
    private Integer code;
    private String message;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
        this.code = BaseErrorCode.SYSTEM_ERROR.code();
        this.message = message;
    }

    public BizException(BaseErrorCodeClass errorCode) {
        super(errorCode.message());
        this.code = errorCode.code();
        this.message = errorCode.message();
    }

    public BizException(BaseErrorCodeClass errorCode, Throwable cause) {
        super(errorCode.message(), cause);
        this.code = errorCode.code();
        this.message = errorCode.message();
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
