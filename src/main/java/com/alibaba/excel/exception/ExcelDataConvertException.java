package com.alibaba.excel.exception;

import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

/**
 * Data convert exception
 *
 * @author Jiaju Zhuang
 */
public class ExcelDataConvertException extends RuntimeException {
    /**
     * NotNull.
     */
    private Integer rowIndex;
    /**
     * NotNull.
     */
    private Integer columnIndex;
    /**
     * Nullable.Only when the header is configured and when the class header is used is not null.
     *
     * @see {@link ExcelWriterBuilder#head(Class)}
     */
    private ExcelContentProperty excelContentProperty;

    public ExcelDataConvertException(String message) {
        super(message);
    }

    public ExcelDataConvertException(Integer rowIndex, Integer columnIndex, ExcelContentProperty excelContentProperty,
        String message) {
        super(message);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.excelContentProperty = excelContentProperty;
    }

    public ExcelDataConvertException(Integer rowIndex, Integer columnIndex, ExcelContentProperty excelContentProperty,
        String message, Throwable cause) {
        super(message, cause);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.excelContentProperty = excelContentProperty;
    }

    public ExcelDataConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelDataConvertException(Throwable cause) {
        super(cause);
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ExcelContentProperty getExcelContentProperty() {
        return excelContentProperty;
    }

    public void setExcelContentProperty(ExcelContentProperty excelContentProperty) {
        this.excelContentProperty = excelContentProperty;
    }
}
