package com.alibaba.easytools.base.excption;

import com.alibaba.easytools.base.enums.BaseErrorEnum;

import lombok.Data;

/**
 * 业务异常。简单的说就是需要人工介入的异常叫做系统异常。
 *
 * @author 是仪
 */
@Data
public class SystemException extends RuntimeException {

    /**
     * 异常的编码
     */
    private String code;

    public SystemException(String message) {
        this(CommonErrorEnum.COMMON_SYSTEM_ERROR, message);
    }

    public SystemException(String message, Throwable throwable) {
        this(CommonErrorEnum.COMMON_SYSTEM_ERROR, message, throwable);
    }

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(BaseErrorEnum errorEnum, String message, Throwable throwable) {
        super(message, throwable);
        this.code = errorEnum.getCode();
    }

    public SystemException(BaseErrorEnum errorEnum) {
        this(errorEnum.getCode(), errorEnum.getDescription());
    }

    public SystemException(BaseErrorEnum errorEnum, String message) {
        this(errorEnum.getCode(), message);
    }

    public static SystemException of(String message) {
        return new SystemException(message);
    }

    public static SystemException of(String code, String message) {
        return new SystemException(code, message);
    }

    public static SystemException of(BaseErrorEnum errorEnum, String message, Throwable throwable) {
        return new SystemException(errorEnum, message, throwable);
    }

    public static SystemException of(BaseErrorEnum errorEnum) {
        return new SystemException(errorEnum);
    }

    public static SystemException of(BaseErrorEnum errorEnum, String message) {
        return new SystemException(errorEnum, message);
    }

}
