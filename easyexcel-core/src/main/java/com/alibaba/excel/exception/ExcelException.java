package com.alibaba.excel.exception;

/**
 * Excel  Exception
 * @author Jiaju Zhuang
 */
public class ExcelException extends RuntimeException {
    public ExcelException() {}

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }
}
