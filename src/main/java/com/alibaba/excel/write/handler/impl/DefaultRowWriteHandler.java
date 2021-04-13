package com.alibaba.excel.write.handler.impl;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

/**
 * Default row handler.
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DefaultRowWriteHandler implements RowWriteHandler {
    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead) {
        writeSheetHolder.setLastRowIndex(row.getRowNum());
    }
}
