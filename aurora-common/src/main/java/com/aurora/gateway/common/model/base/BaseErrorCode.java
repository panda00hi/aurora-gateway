package com.aurora.gateway.common.model.base;

/**
 * <p>
 * 封装基础错误码
 * 自定义异常请在各自模块继承该类扩展
 * </p>
 */
public class BaseErrorCode {

    public static final BaseErrorCodeClass SUCCESS = new BaseErrorCodeClass(200, "success");

    public static final BaseErrorCodeClass SYSTEM_ERROR = new BaseErrorCodeClass(99999, "system error");

}
