package com.alibaba.excel.metadata.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * coordinate.
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CoordinateData {
    /**
     * first row index.Priority is higher than {@link #relativeFirstRowIndex}.
     */
    private Integer firstRowIndex;
    /**
     * first column index.Priority is higher than {@link #relativeFirstColumnIndex}.
     */
    private Integer firstColumnIndex;
    /**
     * last row index.Priority is higher than {@link #relativeLastRowIndex}.
     */
    private Integer lastRowIndex;
    /**
     * last column index.Priority is higher than {@link #relativeLastColumnIndex}.
     */
    private Integer lastColumnIndex;

    /**
     * relative first row index
     */
    private Integer relativeFirstRowIndex;
    /**
     * relative first column index
     */
    private Integer relativeFirstColumnIndex;
    /**
     * relative last row index
     */
    private Integer relativeLastRowIndex;
    /**
     *relative  last column index
     */
    private Integer relativeLastColumnIndex;
}
