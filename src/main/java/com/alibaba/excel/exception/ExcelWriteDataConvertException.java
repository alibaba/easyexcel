package com.alibaba.excel.exception;

import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

import lombok.Data;

/**
 * Data convert exception
 *
 * @author Jiaju Zhuang
 */
@Data
public class ExcelWriteDataConvertException extends ExcelDataConvertException {
    /**
     * handler.
     */
    private CellWriteHandlerContext cellWriteHandlerContext;

    public ExcelWriteDataConvertException(CellWriteHandlerContext cellWriteHandlerContext, String message) {
        super(cellWriteHandlerContext.getRowIndex(), cellWriteHandlerContext.getColumnIndex(),
            cellWriteHandlerContext.getFirstCellData(), cellWriteHandlerContext.getExcelContentProperty(), message);
        this.cellWriteHandlerContext = cellWriteHandlerContext;
    }

    public ExcelWriteDataConvertException(CellWriteHandlerContext cellWriteHandlerContext, String message,
        Throwable cause) {
        super(cellWriteHandlerContext.getRowIndex(), cellWriteHandlerContext.getColumnIndex(),
            cellWriteHandlerContext.getFirstCellData(), cellWriteHandlerContext.getExcelContentProperty(), message,
            cause);
        this.cellWriteHandlerContext = cellWriteHandlerContext;
    }
}
