package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * intercepts handle row creation
 *
 * @author Jiaju Zhuang
 */
public interface RowWriteHandler extends WriteHandler {

    /**
     * Called before create the row
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param rowIndex
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            Nullable.It is null in the case of fill data.
     */
    void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex,
        Integer relativeRowIndex, Boolean isHead);

    /**
     * Called after the row is created
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param row
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            Nullable.It is null in the case of fill data.
     */
    void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead);

    /**
     * Called after all operations on the row have been completed.This method is not called when fill the data.
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable.It is null without using table writes.
     * @param row
     * @param relativeRowIndex
     *            Nullable.It is null in the case of fill data.
     * @param isHead
     *            Nullable.It is null in the case of fill data.
     */
    void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead);
}
