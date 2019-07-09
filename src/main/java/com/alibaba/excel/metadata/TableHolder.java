package com.alibaba.excel.metadata;

import com.alibaba.excel.write.style.CellStyleStrategy;
import com.alibaba.excel.write.style.column.ColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.RowHighStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class TableHolder {
    /***
     * poi sheet
     */
    private SheetHolder parentSheet;
    /**
     * the header attribute of excel
     */
    private ExcelHeadProperty excelHeadProperty;

    private CellStyleStrategy cellStyleStrategy;

    private ColumnWidthStyleStrategy columnWidthStyleStrategy;
    private RowHighStyleStrategy rowHighStyleStrategy;

    private boolean needHead = true;

    /**
     * current table param
     */
    private Table currentTableParam;

    public Table getCurrentTableParam() {
        return currentTableParam;
    }

    public void setCurrentTableParam(Table currentTableParam) {
        this.currentTableParam = currentTableParam;
    }

    public SheetHolder getParentSheet() {
        return parentSheet;
    }

    public void setParentSheet(SheetHolder parentSheet) {
        this.parentSheet = parentSheet;
    }

    public ExcelHeadProperty getExcelHeadProperty() {
        return excelHeadProperty;
    }

    public void setExcelHeadProperty(ExcelHeadProperty excelHeadProperty) {
        this.excelHeadProperty = excelHeadProperty;
    }

    public CellStyleStrategy getCellStyleStrategy() {
        return cellStyleStrategy;
    }

    public void setCellStyleStrategy(CellStyleStrategy cellStyleStrategy) {
        this.cellStyleStrategy = cellStyleStrategy;
    }

    public ColumnWidthStyleStrategy getColumnWidthStyleStrategy() {
        return columnWidthStyleStrategy;
    }

    public void setColumnWidthStyleStrategy(ColumnWidthStyleStrategy columnWidthStyleStrategy) {
        this.columnWidthStyleStrategy = columnWidthStyleStrategy;
    }

    public RowHighStyleStrategy getRowHighStyleStrategy() {
        return rowHighStyleStrategy;
    }

    public void setRowHighStyleStrategy(RowHighStyleStrategy rowHighStyleStrategy) {
        this.rowHighStyleStrategy = rowHighStyleStrategy;
    }

    public boolean isNeedHead() {
        return needHead;
    }

    public void setNeedHead(boolean needHead) {
        this.needHead = needHead;
    }
}
