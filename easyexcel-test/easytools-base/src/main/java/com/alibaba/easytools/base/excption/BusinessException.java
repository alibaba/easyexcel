package com.alibaba.easytools.base.excption;

import com.alibaba.easytools.base.enums.BaseErrorEnum;

import lombok.Data;

/**
 * 业务异常。不需要人工介入的叫做业务异常。
 *
 * @author 是仪
 */
@Data
public class BusinessException extends RuntimeException {
    /**
     * 异常的编码
     */
    private String code;

    public BusinessException(String message) {
        this(CommonErrorEnum.COMMON_BUSINESS_ERROR, message);
    }

    public BusinessException(String message, Throwable throwable) {
        this(CommonErrorEnum.COMMON_BUSINESS_ERROR, message, throwable);
    }

    public BusinessException(BaseErrorEnum errorEnum, String message) {
        this(errorEnum.getCode(), message);
    }

    public BusinessException(BaseErrorEnum errorEnum, String message, Throwable throwable) {
        super(message, throwable);
        this.code = errorEnum.getCode();
    }

    public BusinessException(BaseErrorEnum errorEnum) {
        this(errorEnum.getCode(), errorEnum.getDescription());
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    public static BusinessException of(String code, String message) {
        return new BusinessException(code, message);
    }

    public static BusinessException of(BaseErrorEnum errorEnum, String message, Throwable throwable) {
        return new BusinessException(errorEnum, message, throwable);
    }

    public static BusinessException of(BaseErrorEnum errorEnum) {
        return new BusinessException(errorEnum);
    }

    public static BusinessException of(BaseErrorEnum errorEnum, String message) {
        return new BusinessException(errorEnum, message);
    }
}
