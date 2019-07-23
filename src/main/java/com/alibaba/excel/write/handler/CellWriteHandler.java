package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.SheetHolder;
import com.alibaba.excel.write.metadata.holder.TableHolder;
import com.sun.istack.internal.Nullable;

/**
 * intercepts handle cell creation
 * 
 * @author zhuangjiaju
 */
public interface CellWriteHandler extends WriteHandler {

    /**
     * called before create the cell
     * 
     * @param sheetHolder
     * @param tableHolder
     * @param row
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    void beforeCellCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, Row row, Head head,
                          int relativeRowIndex, boolean isHead);

    /**
     * called after the cell is created
     * 
     * @param sheetHolder
     * @param tableHolder
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    void afterCellCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, Cell cell, Head head,
                         int relativeRowIndex, boolean isHead);
}
