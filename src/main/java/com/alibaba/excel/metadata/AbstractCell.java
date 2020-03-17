package com.alibaba.excel.metadata;

/**
 * cell
 *
 * @author Jiaju Zhuang
 **/
public class AbstractCell implements Cell {
    /**
     * Row index
     */
    private Integer rowIndex;
    /**
     * Column index
     */
    private Integer columnIndex;

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
}
