package com.alibaba.excel.write.handler;

import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;

/**
 * intercepts handle sheet creation
 * 
 * @author zhuangjiaju
 */
public interface SheetWriteHandler extends WriteHandler {

    /**
     * called before create the sheet
     * 
     * @param writeContext
     */
    void beforeSheetCreate(WorkbookHolder workbookHolder, SheetHolder sheetHolder);

    /**
     * called after the sheet is created
     *
     * @param writeContext
     */
    void afterSheetCreate(WorkbookHolder workbookHolder, SheetHolder sheetHolder);
}
