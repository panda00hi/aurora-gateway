package com.aurora.gateway.web.exception;

import com.aurora.gateway.common.model.base.BaseException;
import com.aurora.gateway.common.model.rsp.CommonResult;
import com.aurora.gateway.core.base.ErrorCode;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.List;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public CommonResult<String> handleValidationException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            stringBuilder.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage())
                    .append("|");
        }
        return ErrorCode.createResult(ErrorCode.REQUEST_PARAM_INVALID, stringBuilder);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleException(Exception ex) {
        return exceptionToModel(log, ex);
    }

    @ExceptionHandler(ServletException.class)
    public CommonResult<String> handleServletException(ServletException ex) {
        if (ex instanceof MissingRequestValueException) {
            log.error("here comes error, handleException, error:{}", ex.getMessage());
            return ErrorCode.createResult(ErrorCode.REQUEST_PARAM_MISS, ex.getMessage());
        }
        log.error("here comes error, handleException, ", ex);
        return new CommonResult<>(ErrorCode.SYSTEM_ERROR);
    }

    public static CommonResult<String> exceptionToModel(Logger log, Throwable ex) {
        boolean printDetail = false;
        CommonResult<String> em = null;
        if (ex instanceof IllegalArgumentException) {
            em = new CommonResult<>(ErrorCode.REQUEST_PARAM_INVALID);
        } else if (ex instanceof BaseException) {
            em = new CommonResult<>(((BaseException) ex).getCode(), ex.getMessage(), null);
        } else {
            printDetail = true;
            em = new CommonResult<>(ErrorCode.SYSTEM_ERROR);
        }
        if (printDetail) {
            log.error("here comes error, handleException,", ex);
        } else {
            log.error("here comes error, handleException {}", ex.getMessage());
        }
        return em;
    }

}
