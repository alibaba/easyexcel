package com.alibaba.excel.metadata;

import lombok.Data;

/**
 * cell
 *
 * @author Jiaju Zhuang
 **/
@Data
public class AbstractCell implements Cell {
    /**
     * Row index
     */
    private Integer rowIndex;
    /**
     * Column index
     */
    private Integer columnIndex;
}
