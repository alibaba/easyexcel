package com.alibaba.excel.metadata.data;

import lombok.Data;

/**
 * coordinate.
 *
 * @author Jiaju Zhuang
 */
@Data
public class CoordinateData {
    /**
     * first row index
     */
    private Integer firstRowIndex;
    /**
     * first column index
     */
    private Integer firstColumnIndex;
    /**
     * last row index
     */
    private Integer lastRowIndex;
    /**
     * last column index
     */
    private Integer lastColumnIndex;
}
