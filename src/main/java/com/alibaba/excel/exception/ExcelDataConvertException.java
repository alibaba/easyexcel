package com.alibaba.excel.exception;

import com.alibaba.excel.metadata.CellData;
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
     * NotNull.
     */
    private CellData cellData;
    /**
     * Nullable.Only when the header is configured and when the class header is used is not null.
     *
     * @see ExcelWriterBuilder#head(Class)
     */
    private ExcelContentProperty excelContentProperty;

    public ExcelDataConvertException(Integer rowIndex, Integer columnIndex, CellData cellData,
        ExcelContentProperty excelContentProperty, String message) {
        super(message);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.cellData = cellData;
        this.excelContentProperty = excelContentProperty;
    }

    public ExcelDataConvertException(Integer rowIndex, Integer columnIndex, CellData cellData,
        ExcelContentProperty excelContentProperty, String message, Throwable cause) {
        super(message, cause);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.cellData = cellData;
        this.excelContentProperty = excelContentProperty;
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

    public CellData getCellData() {
        return cellData;
    }

    public void setCellData(CellData cellData) {
        this.cellData = cellData;
    }
}
