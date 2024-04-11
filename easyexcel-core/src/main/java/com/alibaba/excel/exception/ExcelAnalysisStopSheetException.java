package com.alibaba.excel.exception;

/**
 * Throw the exception when you need to stop
 * This exception will only stop the parsing of the current sheet. If you want to stop the entire excel parsing, please
 * use ExcelAnalysisStopException.
 *
 * The com.alibaba.excel.read.listener.ReadListener#doAfterAllAnalysed(com.alibaba.excel.context.AnalysisContext) method
 * is called after the call is stopped.
 *
 * @author Jiaju Zhuang
 * @see ExcelAnalysisStopException
 * @since 3.3.4
 */
public class ExcelAnalysisStopSheetException extends ExcelAnalysisException {

    public ExcelAnalysisStopSheetException() {}

    public ExcelAnalysisStopSheetException(String message) {
        super(message);
    }

    public ExcelAnalysisStopSheetException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelAnalysisStopSheetException(Throwable cause) {
        super(cause);
    }
}
