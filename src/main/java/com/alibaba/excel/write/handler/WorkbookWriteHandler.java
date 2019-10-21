package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * intercepts handle Workbook creation
 *
 * @author Jiaju Zhuang
 */
public interface WorkbookWriteHandler extends WriteHandler {

    /**
     * Called before create the workbook
     */
    void beforeWorkbookCreate();

    /**
     * Called after the workbook is created
     *
     * @param writeWorkbookHolder
     */
    void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder);

    /**
     * Called after all operations on the workbook have been completed
     *
     * @param writeWorkbookHolder
     */
    void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder);
}
