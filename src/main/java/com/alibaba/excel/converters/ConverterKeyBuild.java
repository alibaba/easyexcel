package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;

/**
 * Converter unique key
 *
 * @author Jiaju Zhuang
 */
public class ConverterKeyBuild {
    public static String buildKey(Class clazz) {
        return clazz.getName();
    }

    public static String buildKey(Class clazz, CellDataTypeEnum cellDataTypeEnum) {
        return clazz.getName() + "-" + cellDataTypeEnum.toString();
    }
}
