package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.holder.SheetHolder;

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
    void beforeSheetCreate(Workbook workbook, SheetHolder sheetHolder);

    /**
     * called after the sheet is created
     *
     * @param writeContext
     */
    void afterSheetCreate(Workbook workbook, SheetHolder sheetHolder);
}
