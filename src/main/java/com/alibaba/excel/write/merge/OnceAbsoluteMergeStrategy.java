package com.alibaba.excel.write.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;

/**
 * It only merges once when create cell(firstRowIndex,lastRowIndex)
 *
 * @author Jiaju Zhuang
 */
public class OnceAbsoluteMergeStrategy extends AbstractMergeStrategy {

    private int firstRowIndex;
    private int lastRowIndex;
    private int firstColumnIndex;
    private int lastColumnIndex;

    public OnceAbsoluteMergeStrategy(int firstRowIndex, int lastRowIndex, int firstColumnIndex, int lastColumnIndex) {
        if (firstRowIndex < 0 || lastRowIndex < 0 || firstColumnIndex < 0 || lastColumnIndex < 0) {
            throw new IllegalArgumentException("All parameters must be greater than 0");
        }
        this.firstRowIndex = firstRowIndex;
        this.lastRowIndex = lastRowIndex;
        this.firstColumnIndex = firstColumnIndex;
        this.lastColumnIndex = lastColumnIndex;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, int relativeRowIndex) {
        if (cell.getRowIndex() == firstRowIndex && cell.getColumnIndex() == firstColumnIndex) {
            CellRangeAddress cellRangeAddress =
                new CellRangeAddress(firstRowIndex, lastRowIndex, firstColumnIndex, lastColumnIndex);
            sheet.addMergedRegion(cellRangeAddress);
        }
    }
}
