package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.TableHolder;
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
     * @param writeContext
     */
    void beforeRowCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, int rowIndex, int relativeRowIndex,
        boolean isHead);

    /**
     * called after the row is created
     *
     * @param writeContext
     */
    void afterRowCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, Row row, int relativeRowIndex,
        boolean isHead);
}
