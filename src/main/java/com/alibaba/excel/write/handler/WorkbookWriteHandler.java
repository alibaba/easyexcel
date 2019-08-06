package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * intercepts handle Workbook creation
 *
 * @author Jiaju Zhuang
 */
public interface WorkbookWriteHandler extends WriteHandler {

    /**
     * called before create the sheet
     */
    void beforeWorkbookCreate();

    /**
     * called after the sheet is created
     *
     * @param writeWorkbookHolder
     */
    void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder);
}
