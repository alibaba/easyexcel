package com.alibaba.excel.metadata;

import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.write.style.CellStyleStrategy;
import com.alibaba.excel.write.style.column.ColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.RowHighStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class SheetHolder {
    /***
     * poi sheet
     */
    private Sheet sheet;
    /***
     * has been initialized table
     */
    private Map<Integer, TableHolder> hasBeenInitializedTable;
    /**
     * the header attribute of excel
     */
    private ExcelHeadProperty excelHeadProperty;

    private CellStyleStrategy cellStyleStrategy;

    private ColumnWidthStyleStrategy columnWidthStyleStrategy;
    private RowHighStyleStrategy rowHighStyleStrategy;
    /**
     * current param
     */
    private com.alibaba.excel.metadata.Sheet currentSheetParam;

    private boolean needHead = true;

    public com.alibaba.excel.metadata.Sheet getCurrentSheetParam() {
        return currentSheetParam;
    }

    public void setCurrentSheetParam(com.alibaba.excel.metadata.Sheet currentSheetParam) {
        this.currentSheetParam = currentSheetParam;
    }

    public RowHighStyleStrategy getRowHighStyleStrategy() {
        return rowHighStyleStrategy;
    }

    public void setRowHighStyleStrategy(RowHighStyleStrategy rowHighStyleStrategy) {
        this.rowHighStyleStrategy = rowHighStyleStrategy;
    }

    public ColumnWidthStyleStrategy getColumnWidthStyleStrategy() {
        return columnWidthStyleStrategy;
    }

    public void setColumnWidthStyleStrategy(ColumnWidthStyleStrategy columnWidthStyleStrategy) {
        this.columnWidthStyleStrategy = columnWidthStyleStrategy;
    }

    public boolean isNeedHead() {
        return needHead;
    }

    public void setNeedHead(boolean needHead) {
        this.needHead = needHead;
    }

    public ExcelHeadProperty getExcelHeadProperty() {
        return excelHeadProperty;
    }

    public void setExcelHeadProperty(ExcelHeadProperty excelHeadProperty) {
        this.excelHeadProperty = excelHeadProperty;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Map<Integer, TableHolder> getHasBeenInitializedTable() {
        return hasBeenInitializedTable;
    }

    public void setHasBeenInitializedTable(Map<Integer, TableHolder> hasBeenInitializedTable) {
        this.hasBeenInitializedTable = hasBeenInitializedTable;
    }

    public CellStyleStrategy getCellStyleStrategy() {
        return cellStyleStrategy;
    }

    public void setCellStyleStrategy(CellStyleStrategy cellStyleStrategy) {
        this.cellStyleStrategy = cellStyleStrategy;
    }
}
