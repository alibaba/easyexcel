package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.SheetHolder;
import com.alibaba.excel.metadata.TableHolder;
import com.sun.istack.internal.Nullable;

/**
 * intercepts handle cell creation
 * 
 * @author zhuangjiaju
 */
public interface CellExcelWriteHandler extends ExcelWriteHandler {

    /**
     * called before create the cell
     * 
     * @param writeContext
     */
    void beforeCellCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, Row row, Head head,
        int relativeRowIndex, boolean isHead);

    /**
     * called after the cell is created
     *
     * @param writeContext
     */
    void afterCellCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, Cell cell, Head head,
        int relativeRowIndex, boolean isHead);
}
