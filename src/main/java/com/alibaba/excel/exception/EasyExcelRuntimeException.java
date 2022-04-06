package com.alibaba.excel.exception;

/**
 * Underlying exception uniform construction,
 * <a href="https://github.com/alibaba/easyexcel/issues/2375">...</a>
 */
public class EasyExcelRuntimeException extends RuntimeException {

    public EasyExcelRuntimeException() {
    }

    public EasyExcelRuntimeException(String message) {
        super(message);
    }

    public EasyExcelRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyExcelRuntimeException(Throwable cause) {
        super(cause);
    }
}
