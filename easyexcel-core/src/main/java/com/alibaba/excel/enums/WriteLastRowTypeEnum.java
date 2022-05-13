package com.alibaba.excel.enums;

/**
 * The types of write last row
 *
 * @author Jiaju Zhuang
 **/
public enum WriteLastRowTypeEnum {
    /**
     * Excel are created without templates ,And any data has been written;
     */
    COMMON_EMPTY,
    /**
     * Excel are created with templates ,And any data has been written;
     */
    TEMPLATE_EMPTY,
    /**
     * Any data has been written;
     */
    HAS_DATA,;
}
