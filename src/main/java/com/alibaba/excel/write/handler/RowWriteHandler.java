package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.write.metadata.holder.SheetHolder;
import com.alibaba.excel.write.metadata.holder.TableHolder;
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
     * @param sheetHolder
     * @param tableHolder
     * @param rowIndex
     * @param relativeRowIndex
     * @param isHead
     */
    void beforeRowCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, int rowIndex, int relativeRowIndex,
                         boolean isHead);

    /**
     * called after the row is created
     * 
     * @param sheetHolder
     * @param tableHolder
     * @param row
     * @param relativeRowIndex
     * @param isHead
     */
    void afterRowCreate(SheetHolder sheetHolder, @Nullable TableHolder tableHolder, Row row, int relativeRowIndex,
                        boolean isHead);
}
