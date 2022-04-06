package com.alibaba.excel.exception;

/**
 * @author jipengfei
 */
public class ExcelGenerateException extends EasyExcelRuntimeException {

    public ExcelGenerateException(String message) {
        super(message);
    }

    public ExcelGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelGenerateException(Throwable cause) {
        super(cause);
    }
}
