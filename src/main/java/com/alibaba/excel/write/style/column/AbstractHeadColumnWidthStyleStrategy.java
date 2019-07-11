package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.metadata.Head;

/**
 * Returns the column width according to each column header
 * 
 * @author zhuangjiaju
 */
public abstract class AbstractHeadColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    @Override
    protected void setColumnWidth(Sheet sheet, Cell cell, Head head) {
        sheet.setColumnWidth(cell.getColumnIndex(), columnWidth(head));
    }

    /**
     * Returns the column width corresponding to each column head
     * 
     * @param head
     * @return
     */
    protected abstract int columnWidth(Head head);
}
