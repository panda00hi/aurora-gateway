package com.aurora.gateway.common.model.base;

/**
 * 错误码定义类
 * 代替枚举，便于相关模块继承和扩展
 *
 * @author panda00hi
 * @date 2023.11.15
 */
public record BaseErrorCodeClass(Integer code, String message) {

    @Override
    public String toString() {
        return "BaseErrorCodeClass(code=" + this.code() + ", message=" + this.message() + ")";

    }
}
