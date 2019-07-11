package com.alibaba.excel.exception;

/**
 * Data convert exception
 * 
 * @author zhuangjiaju
 */
public class ExcelDataConvertException extends RuntimeException {

    public ExcelDataConvertException(String message) {
        super(message);
    }

    public ExcelDataConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelDataConvertException(Throwable cause) {
        super(cause);
    }
}
