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
     * called before create the row
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable
     * @param rowIndex
     * @param relativeRowIndex
     * @param isHead
     */
    void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, int rowIndex,
        int relativeRowIndex, boolean isHead);

    /**
     * called after the row is created
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     *            Nullable
     * @param row
     * @param relativeRowIndex
     * @param isHead
     */
    void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        int relativeRowIndex, boolean isHead);
}
