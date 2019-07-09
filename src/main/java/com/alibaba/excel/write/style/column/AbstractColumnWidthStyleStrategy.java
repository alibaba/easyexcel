package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.SheetHolder;
import com.alibaba.excel.metadata.TableHolder;
import com.alibaba.excel.write.handler.CellExcelWriteHandler;

/**
 * Column width style strategy
 * 
 * @author zhuangjiaju
 */
public abstract class AbstractColumnWidthStyleStrategy implements CellExcelWriteHandler {

    @Override
    public void beforeCellCreate(SheetHolder sheetHolder, TableHolder tableHolder, Row row, Head head,
        int relativeRowIndex, boolean isHead) {}

    @Override
    public void afterCellCreate(SheetHolder sheetHolder, TableHolder tableHolder, Cell cell, Head head,
        int relativeRowIndex, boolean isHead) {
        if (!isHead) {
            return;
        }
        setColumnWidth(sheetHolder.getSheet(), cell, head);
    }

    /**
     * Sets the column width when head create
     * 
     * @param sheet
     * @param cell
     * @param head
     */
    protected abstract void setColumnWidth(Sheet sheet, Cell cell, Head head);

}
