package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * Abstract sheet write handler
 *
 * @author Jiaju Zhuang
 * @deprecated Please use it directly {@link SheetWriteHandler}
 **/
@Deprecated
public abstract class AbstractSheetWriteHandler implements SheetWriteHandler {
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }
}
