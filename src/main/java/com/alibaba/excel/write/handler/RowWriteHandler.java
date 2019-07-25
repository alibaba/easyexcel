package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.sun.istack.internal.Nullable;

/**
 * intercepts handle row creation
 * 
 * @author zhuangjiaju
 */
public interface RowWriteHandler extends WriteHandler {

    /**
     * called before create the row
     * 
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param rowIndex
     * @param relativeRowIndex
     * @param isHead
     */
    void beforeRowCreate(WriteSheetHolder writeSheetHolder, @Nullable WriteTableHolder writeTableHolder, int rowIndex,
        int relativeRowIndex, boolean isHead);

    /**
     * called after the row is created
     * 
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param row
     * @param relativeRowIndex
     * @param isHead
     */
    void afterRowCreate(WriteSheetHolder writeSheetHolder, @Nullable WriteTableHolder writeTableHolder, Row row,
        int relativeRowIndex, boolean isHead);
}
