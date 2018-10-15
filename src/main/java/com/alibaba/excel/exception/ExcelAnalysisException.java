package com.alibaba.excel.exception;

/**
 *
 * @author jipengfei
 */
public class ExcelAnalysisException extends RuntimeException {

    public ExcelAnalysisException() {
    }

    public ExcelAnalysisException(String message) {
        super(message);
    }

    public ExcelAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelAnalysisException(Throwable cause) {
        super(cause);
    }
}
