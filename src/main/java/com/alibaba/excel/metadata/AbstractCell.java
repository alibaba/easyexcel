package com.alibaba.excel.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * cell
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
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
