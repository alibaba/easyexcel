package com.alibaba.excel.exception;

/**
 * Excel  Exception
 * @author Jiaju Zhuang
 */
public class ExcelRuntimeException extends RuntimeException {
    public ExcelRuntimeException() {}

    public ExcelRuntimeException(String message) {
        super(message);
    }

    public ExcelRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelRuntimeException(Throwable cause) {
        super(cause);
    }
}
