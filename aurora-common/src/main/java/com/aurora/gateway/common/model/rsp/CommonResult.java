package com.aurora.gateway.common.model.rsp;


import com.aurora.gateway.common.model.base.BaseErrorCode;
import com.aurora.gateway.common.model.base.BaseErrorCodeClass;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author panda00hi
 * @date 2023.11.13
 */
public class CommonResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public CommonResult() {
    }

    public CommonResult(BaseErrorCodeClass errorCode) {
        this.code = errorCode.code();
        this.message = errorCode.message();
    }

    public CommonResult(BaseErrorCodeClass errorCode, T data) {
        this.code = errorCode.code();
        this.message = errorCode.message();
        this.data = data;
    }


    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setErrorCode(BaseErrorCodeClass errorCode) {
        this.code = errorCode.code();
        this.message = errorCode.message();
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(BaseErrorCode.SUCCESS, null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(BaseErrorCode.SUCCESS, data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(BaseErrorCode.SUCCESS.code(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(BaseErrorCodeClass errorCode) {
        return new CommonResult<T>(errorCode, null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> CommonResult<T> failed(BaseErrorCodeClass errorCode, String message) {
        return new CommonResult<T>(errorCode.code(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(BaseErrorCode.SYSTEM_ERROR.code(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(BaseErrorCode.SYSTEM_ERROR);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
