package com.alibaba.excel.write.merge;

import java.util.List;

import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Merge strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractMergeStrategy implements CellWriteHandler {

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }
        merge(writeSheetHolder.getSheet(), cell, head, relativeRowIndex);
    }

    /**
     * merge
     *
     * @param sheet
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected abstract void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex);
}
