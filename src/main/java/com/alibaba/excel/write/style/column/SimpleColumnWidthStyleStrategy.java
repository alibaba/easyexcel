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

    /**
     * Using the Calibri font as an example, the maximum digit width of 11 point font size is 7 pixels (at 96 dpi). * If
     * you set a column width to be eight characters wide, e.g. <code>SimpleColumnWidthStyleStrategy( 8*256)</code>,
     * 
     * @param columnWidth
     *            the width in units of 1/256th of a character width
     */
    public SimpleColumnWidthStyleStrategy(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    protected void setColumnWidth(Sheet sheet, Cell cell, Head head) {
        sheet.setColumnWidth(cell.getColumnIndex(), columnWidth);
    }
}
