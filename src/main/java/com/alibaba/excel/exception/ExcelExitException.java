package com.alibaba.excel.exception;

/**
 * @description Take the initiative to stop Analysis
 * @author kaywall
 * @date 2019/10/15 18:00
 */
public class ExcelExitException extends ExcelAnalysisException {

    public ExcelExitException() {}

    public ExcelExitException(String message) {
        super(message);
    }

    public ExcelExitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelExitException(Throwable cause) {
        super(cause);
    }
}
