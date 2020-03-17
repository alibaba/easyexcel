package com.alibaba.excel.write.handler;

import java.util.List;

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
     * Called before create the cell
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param row
     * @param head
     *            Nullable.It is null in the case of fill data and without head.
     * @param columnIndex
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            It will always be false when fill data.
     */
    void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head,
        Integer columnIndex, Integer relativeRowIndex, Boolean isHead);

    /**
     * Called after the cell is created
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param cell
     * @param head
     *            Nullable.It is null in the case of fill data and without head.
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            It will always be false when fill data.
     */
    void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head,
        Integer relativeRowIndex, Boolean isHead);

    /**
     * Called after the cell data is converted
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param cell
     * @param head
     *            Nullable.It is null in the case of fill data and without head.
     * @param cellData
     *            Nullable.It is null in the case of add header.
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            It will always be false when fill data.
     */
    void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData,
        Cell cell, Head head, Integer relativeRowIndex, Boolean isHead);

    /**
     * Called after all operations on the cell have been completed
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param cell
     * @param head
     *            Nullable.It is null in the case of fill data and without head.
     * @param cellDataList
     *            Nullable.It is null in the case of add header.There may be several when fill the data.
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            It will always be false when fill data.
     */
    void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead);
}
