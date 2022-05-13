package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import org.apache.poi.ss.usermodel.Row;

/**
 * intercepts handle row creation
 *
 * @author Jiaju Zhuang
 */
public interface RowWriteHandler extends WriteHandler {

    /**
     * Called before create the row
     *
     * @param context
     */
    default void beforeRowCreate(RowWriteHandlerContext context) {
        beforeRowCreate(context.getWriteSheetHolder(), context.getWriteTableHolder(), context.getRowIndex(),
            context.getRelativeRowIndex(), context.getHead());
    }

    /**
     * Called before create the row
     *
     * @param writeSheetHolder
     * @param writeTableHolder Nullable.It is null without using table writes.
     * @param rowIndex
     * @param relativeRowIndex Nullable.It is null in the case of fill data.
     * @param isHead           Nullable.It is null in the case of fill data.
     */
    default void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex,
        Integer relativeRowIndex, Boolean isHead) {}

    /**
     * Called after the row is created
     *
     * @param context
     */
    default void afterRowCreate(RowWriteHandlerContext context) {
        afterRowCreate(context.getWriteSheetHolder(), context.getWriteTableHolder(), context.getRow(),
            context.getRelativeRowIndex(), context.getHead());
    }

    /**
     * Called after the row is created
     *
     * @param writeSheetHolder
     * @param writeTableHolder Nullable.It is null without using table writes.
     * @param row
     * @param relativeRowIndex Nullable.It is null in the case of fill data.
     * @param isHead           Nullable.It is null in the case of fill data.
     */
    default void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead) {}

    /**
     * Called after all operations on the row have been completed.
     * In the case of the fill , may be called many times.
     *
     * @param context
     */
    default void afterRowDispose(RowWriteHandlerContext context) {
        afterRowDispose(context.getWriteSheetHolder(), context.getWriteTableHolder(), context.getRow(),
            context.getRelativeRowIndex(), context.getHead());
    }

    /**
     * Called after all operations on the row have been completed.
     * In the case of the fill , may be called many times.
     *
     * @param writeSheetHolder
     * @param writeTableHolder Nullable.It is null without using table writes.
     * @param row
     * @param relativeRowIndex Nullable.It is null in the case of fill data.
     * @param isHead           Nullable.It is null in the case of fill data.
     */
    default void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead) {}
}
