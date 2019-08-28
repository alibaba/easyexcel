package com.alibaba.excel.write.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;

/**
 * The regions of the loop merge
 *
 * @author Jiaju Zhuang
 */
public class LoopMergeStrategy extends AbstractMergeStrategy {
    private int eachRow;
    private int columnIndex;

    public LoopMergeStrategy(int eachRow, int columnIndex) {
        if (eachRow < 1) {
            throw new IllegalArgumentException("EachRows must be greater than 1");
        }
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.eachRow = eachRow;
        this.columnIndex = columnIndex;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, int relativeRowIndex) {
        if (head.getColumnIndex() == columnIndex && relativeRowIndex % eachRow == 0) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(cell.getRowIndex(),
                cell.getRowIndex() + eachRow - 1, cell.getColumnIndex(), cell.getColumnIndex());
            sheet.addMergedRegion(cellRangeAddress);
        }
    }
}
