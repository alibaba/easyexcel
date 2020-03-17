package com.alibaba.excel.write.merge;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.property.LoopMergeProperty;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * The regions of the loop merge
 *
 * @author Jiaju Zhuang
 */
public class LoopMergeStrategy extends AbstractRowWriteHandler {
    /**
     * Each row
     */
    private int eachRow;
    /**
     * Extend column
     */
    private int columnExtend;
    /**
     * The number of the current column
     */
    private int columnIndex;

    public LoopMergeStrategy(int eachRow, int columnIndex) {
        this(eachRow, 1, columnIndex);
    }

    public LoopMergeStrategy(int eachRow, int columnExtend, int columnIndex) {
        if (eachRow < 1) {
            throw new IllegalArgumentException("EachRows must be greater than 1");
        }
        if (columnExtend < 1) {
            throw new IllegalArgumentException("ColumnExtend must be greater than 1");
        }
        if (columnExtend == 1 && eachRow == 1) {
            throw new IllegalArgumentException("ColumnExtend or eachRows must be greater than 1");
        }
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.eachRow = eachRow;
        this.columnExtend = columnExtend;
        this.columnIndex = columnIndex;
    }

    public LoopMergeStrategy(LoopMergeProperty loopMergeProperty, Integer columnIndex) {
        this(loopMergeProperty.getEachRow(), loopMergeProperty.getColumnExtend(), columnIndex);
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }
        if (relativeRowIndex % eachRow == 0) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum(), row.getRowNum() + eachRow - 1,
                columnIndex, columnIndex + columnExtend - 1);
            writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
        }
    }

}
