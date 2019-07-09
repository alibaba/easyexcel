package com.alibaba.excel.write.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;

/**
 * The regions of the loop merge
 * 
 * @author zhuangjiaju
 */
public class LoopMergeStrategy extends AbstractMergeStrategy {
    private int eachRow;
    private int eachColumn;

    public LoopMergeStrategy(int eachRow, int eachColumn) {
        if (eachRow < 1 || eachColumn < 1) {
            throw new IllegalArgumentException("All parameters must be greater than 1");
        }
        this.eachRow = eachRow;
        this.eachColumn = eachColumn;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, int relativeRowIndex) {
        if (relativeRowIndex % eachRow == 0 && head.getColumnIndex() % eachColumn == 0) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex() + eachRow,
                cell.getColumnIndex(), cell.getColumnIndex() + eachColumn);
            sheet.addMergedRegion(cellRangeAddress);
        }
    }
}
