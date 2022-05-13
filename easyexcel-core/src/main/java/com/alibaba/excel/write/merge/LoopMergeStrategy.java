package com.alibaba.excel.write.merge;

import com.alibaba.excel.metadata.property.LoopMergeProperty;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;

import org.apache.poi.ss.util.CellRangeAddress;

/**
 * The regions of the loop merge
 *
 * @author Jiaju Zhuang
 */
public class LoopMergeStrategy implements RowWriteHandler {
    /**
     * Each row
     */
    private final int eachRow;
    /**
     * Extend column
     */
    private final int columnExtend;
    /**
     * The number of the current column
     */
    private final int columnIndex;

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
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (context.getHead() || context.getRelativeRowIndex() == null) {
            return;
        }
        if (context.getRelativeRowIndex() % eachRow == 0) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(context.getRowIndex(),
                context.getRowIndex() + eachRow - 1,
                columnIndex, columnIndex + columnExtend - 1);
            context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
        }
    }

}
