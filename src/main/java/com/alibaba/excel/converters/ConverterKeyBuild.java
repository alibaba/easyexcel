package com.alibaba.excel.converters;

import java.util.Map;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.util.MapUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Converter unique key.Consider that you can just use class as the key.
 *
 * @author Jiaju Zhuang
 */
public class ConverterKeyBuild {

    private static final Map<Class<?>, Class<?>> BOXING_MAP = MapUtils.newHashMap();

    static {
        BOXING_MAP.put(int.class, Integer.class);
        BOXING_MAP.put(byte.class, Byte.class);
        BOXING_MAP.put(long.class, Long.class);
        BOXING_MAP.put(double.class, Double.class);
        BOXING_MAP.put(float.class, Float.class);
        BOXING_MAP.put(char.class, Character.class);
        BOXING_MAP.put(short.class, Short.class);
        BOXING_MAP.put(boolean.class, Boolean.class);
    }

    public static ConverterKey buildKey(Class<?> clazz) {
        return buildKey(clazz, null);
    }

    public static ConverterKey buildKey(Class<?> clazz, CellDataTypeEnum cellDataTypeEnum) {
        Class<?> boxingClass = BOXING_MAP.get(clazz);
        if (boxingClass != null) {
            return new ConverterKey(boxingClass, cellDataTypeEnum);
        }
        return new ConverterKey(clazz, cellDataTypeEnum);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class ConverterKey {
        private Class<?> clazz;
        private CellDataTypeEnum cellDataTypeEnum;
    }
}
