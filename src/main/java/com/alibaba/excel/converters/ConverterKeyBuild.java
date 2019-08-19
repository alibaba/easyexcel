package com.alibaba.excel.converters;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.enums.CellDataTypeEnum;

/**
 * Converter unique key.Consider that you can just use class as the key.
 *
 * @author Jiaju Zhuang
 */
public class ConverterKeyBuild {

    private static final Map<String, String> BOXING_MAP = new HashMap<String, String>(16);

    static {
        BOXING_MAP.put(int.class.getName(), Integer.class.getName());
        BOXING_MAP.put(byte.class.getName(), Byte.class.getName());
        BOXING_MAP.put(long.class.getName(), Long.class.getName());
        BOXING_MAP.put(double.class.getName(), Double.class.getName());
        BOXING_MAP.put(float.class.getName(), Float.class.getName());
        BOXING_MAP.put(char.class.getName(), Character.class.getName());
        BOXING_MAP.put(short.class.getName(), Short.class.getName());
        BOXING_MAP.put(boolean.class.getName(), Boolean.class.getName());
    }

    public static String buildKey(Class clazz) {
        String className = clazz.getName();
        String boxingClassName = BOXING_MAP.get(clazz.getName());
        if (boxingClassName == null) {
            return className;
        }
        return boxingClassName;
    }

    public static String buildKey(Class clazz, CellDataTypeEnum cellDataTypeEnum) {
        return buildKey(clazz) + "-" + cellDataTypeEnum.toString();
    }
}
