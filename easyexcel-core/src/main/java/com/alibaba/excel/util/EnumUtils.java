package com.alibaba.excel.util;

import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.property.EnumFormatProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.InvocationTargetException;

/**
 * Enum utils
 *
 * @author xiajiafu
 * @since 2022/7/14
 */
public class EnumUtils {

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
        EnumFormatProperty formatProperty = contentProperty.getEnumFormatProperty();
        Class<? extends Enum<?>> clazz = formatProperty.getTargetClass();
        String method = formatProperty.getConvertToExcelDataMethod();
        try {
            res = (String) clazz.getMethod(method, value.getClass()).invoke(clazz, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ExcelCommonException("convert enum value fail.", e);
        }
        return res;
    }

}
