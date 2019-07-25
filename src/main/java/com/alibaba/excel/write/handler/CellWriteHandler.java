package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
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
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param row
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    void beforeCellCreate(WriteSheetHolder writeSheetHolder, @Nullable WriteTableHolder writeTableHolder, Row row,
        Head head, int relativeRowIndex, boolean isHead);

    /**
     * called after the cell is created
     * 
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    void afterCellCreate(WriteSheetHolder writeSheetHolder, @Nullable WriteTableHolder writeTableHolder, Cell cell,
        Head head, int relativeRowIndex, boolean isHead);
}
