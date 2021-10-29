package com.alibaba.excel.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jipengfei
 */
@Getter
@Setter
@EqualsAndHashCode
public class CellRange {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;

    public CellRange(int firstRow, int lastRow, int firstCol, int lastCol) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }
}
