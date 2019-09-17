package com.alibaba.excel.util;

/**
 * String utils
 *
 * @author jipengfei
 */
public class StringUtils {

    private StringUtils() {}

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }
}
