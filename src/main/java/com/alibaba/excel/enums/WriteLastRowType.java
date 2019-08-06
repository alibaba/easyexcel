package com.alibaba.excel.enums;

/**
 * The types of write last row
 *
 * @author Jiaju Zhuang
 **/
public enum WriteLastRowType {
    /**
     * Tables are created without templates ,And any data has been written;
     */
    EMPTY,
    /**
     * It's supposed to have data in it.Tables are created with templates ,or any data has been written;
     */
    HAVE_DATA,;
}
