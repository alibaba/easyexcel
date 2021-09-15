package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * Abstract workbook write handler
 *
 * @author Jiaju Zhuang
 * @deprecated Please use it directly {@link WorkbookWriteHandler}
 **/
@Deprecated
public abstract class AbstractWorkbookWriteHandler implements WorkbookWriteHandler {

    @Override
    public void beforeWorkbookCreate() {

    }

    @Override
    public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {

    }

    @Override
    public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {

    }
}
