package com.alibaba.excel.exception;

/**
 * Throw the exception when you need to stop
 * This exception will stop the entire excel parsing. If you only want to stop the parsing of a certain sheet, please
 * use ExcelAnalysisStopSheetException.
 *
 * @author Jiaju Zhuang
 * @see ExcelAnalysisStopException
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
