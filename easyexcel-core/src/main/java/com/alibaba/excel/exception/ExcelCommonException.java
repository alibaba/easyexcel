package com.alibaba.excel.exception;

/**
 *
 * @author Jiaju Zhuang
 */
public class ExcelCommonException extends RuntimeException {

    public ExcelCommonException() {}

    public ExcelCommonException(String message) {
        super(message);
    }

    public ExcelCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelCommonException(Throwable cause) {
        super(cause);
    }
}
