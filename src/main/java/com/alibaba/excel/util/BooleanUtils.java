package com.alibaba.excel.util;

/**
 * boolean util
 *
 * @author Jiaju Zhuang
 */
public class BooleanUtils {

    private static final String TRUE_NUMBER = "1";
    private static final String FALSE_NUMBER = "0";

    /**
     * String to boolean
     *
     * <li>
     *
     * @param str
     * @return
     */
    public static Boolean valueOf(String str) {
        if (TRUE_NUMBER.equals(str)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

}
