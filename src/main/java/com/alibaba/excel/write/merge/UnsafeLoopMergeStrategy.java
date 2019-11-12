package com.alibaba.excel.write.merge;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Faster than {@link LoopMergeStrategy}, when merging cells in large numbers
 * @author pdkst
 */
public class UnsafeLoopMergeStrategy extends LoopMergeStrategy{
    public UnsafeLoopMergeStrategy(int eachRow, int columnIndex) {
        super(eachRow, columnIndex);
    }

    public UnsafeLoopMergeStrategy(int eachRow, int columnCount, int columnIndex) {
        super(eachRow, columnCount, columnIndex);
    }

    @Override
    protected void addMergedRegion(Sheet sheet, CellRangeAddress cellRangeAddress) {
        sheet.addMergedRegionUnsafe(cellRangeAddress);
    }
}
