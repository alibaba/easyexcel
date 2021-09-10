package com.alibaba.excel.enums;

import org.apache.poi.ss.usermodel.CellType;

/**
 * Used to supplement {@link CellType}.
 *
 * Cannot distinguish between date and number in write case.
 *
 * @author Jiaju Zhuang
 */
public enum NumericCellTypeEnum {
    /**
     * number
     */
    NUMBER,
    /**
     * date. Support only when writing.
     */
    DATE,
    ;
}
