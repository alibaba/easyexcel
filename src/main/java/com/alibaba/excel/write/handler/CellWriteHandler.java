package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * intercepts handle cell creation
 *
 * @author Jiaju Zhuang
 */
public interface CellWriteHandler extends WriteHandler {

    /**
     * called before create the cell
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable
     * @param row
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head,
        int relativeRowIndex, boolean isHead);

    /**
     * called after the cell is created
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable
     * @param cell
     * @param head
     * @param cellData
     *            Nullable.
     * @param relativeRowIndex
     * @param isHead
     */
    void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData,
        Cell cell, Head head, int relativeRowIndex, boolean isHead);
}
