package com.alibaba.excel.enums;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.util.StringUtils;

/**
 * Read not to {@code com.alibaba.excel.metadata.BasicParameter#clazz} value, the default will return type.
 *
 * @author Jiaju Zhuang
 */
public enum ReadDefaultReturnEnum {
    /**
     * default.The content of cells into string, is the same as you see in the excel.
     */
    STRING,

    /**
     * Returns the actual type.
     * Will be automatically selected according to the cell contents what return type, will return the following class:
     * <ol>
     *     <li>{@link BigDecimal}</li>
     *     <li>{@link Boolean}</li>
     *     <li>{@link String}</li>
     *     <li>{@link LocalDateTime}</li>
     * </ol>
     */
    ACTUAL_DATA,

    /**
     * Return to {@link com.alibaba.excel.metadata.data.ReadCellData}, can decide which field you need.
     */
    READ_CELL_DATA,
    ;

}
