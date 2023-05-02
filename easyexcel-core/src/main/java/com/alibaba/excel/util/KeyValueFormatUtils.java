package com.alibaba.excel.util;

import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.KeyValueFormatProperty;

import java.lang.reflect.InvocationTargetException;

/**
 * key value format utils
 *
 * @author xiajiafu
 * @since 2022/7/14
 */
public class KeyValueFormatUtils {

    /**
     * format to java data.
     *
     * @param value
     * @param contentProperty
     * @return
     */
    public static Object formatToJavaData(String value, ExcelContentProperty contentProperty) {
        Object res = null;
        if (value == null) {
            return res;
        }
        KeyValueFormatProperty formatProperty = contentProperty.getKeyValueFormatProperty();
        Class<?> clazz = formatProperty.getTargetClass();
        String method = formatProperty.getJavaify();
        try {
            res = clazz.getMethod(method, value.getClass()).invoke(clazz, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ExcelCommonException("convert enum value fail.", e);
        }
        return res;
    }

    /**
     * format to cell data.
     *
     * @param value
     * @param contentProperty
     * @param <T>
     * @return
     */
    public static <T> String formatToCellData(T value, ExcelContentProperty contentProperty) {
        String res = null;
        if (value == null) {
            return res;
        }
        KeyValueFormatProperty formatProperty = contentProperty.getKeyValueFormatProperty();
        Class<?> clazz = formatProperty.getTargetClass();
        String method = formatProperty.getExcelify();
        try {
            res = (String) clazz.getMethod(method, value.getClass()).invoke(clazz, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ExcelCommonException("convert enum value fail.", e);
        }
        return res;
    }

}
