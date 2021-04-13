package com.alibaba.easyexcel.test.temp.simple;

import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jiaju Zhuang
 */
@Slf4j
public class WriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder,
        WriteSheetHolder writeSheetHolder) {
        log.info("锁住");
        writeSheetHolder.getSheet().protectSheet("edit");
    }
}
