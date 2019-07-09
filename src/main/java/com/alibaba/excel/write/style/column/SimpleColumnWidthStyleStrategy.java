package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.metadata.Head;

/**
 * All the columns are the same width
 * 
 * @author zhuangjiaju
 */
public class SimpleColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    private int columnWidth;

    public SimpleColumnWidthStyleStrategy(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    protected void setColumnWidth(Sheet sheet, Cell cell, Head head) {
        sheet.setColumnWidth(cell.getColumnIndex(), columnWidth);
    }
}
