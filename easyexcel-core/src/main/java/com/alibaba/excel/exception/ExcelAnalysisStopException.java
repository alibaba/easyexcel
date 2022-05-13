package com.alibaba.excel.exception;

/**
 * Throw the exception when you need to stop
 *
 * @author Jiaju Zhuang
 */
public class ExcelAnalysisStopException extends ExcelAnalysisException {

    public ExcelAnalysisStopException() {}

    public ExcelAnalysisStopException(String message) {
        super(message);
    }

    public ExcelAnalysisStopException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelAnalysisStopException(Throwable cause) {
        super(cause);
    }
}
