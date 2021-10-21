package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;

import com.alibaba.excel.converters.Converter;

import lombok.Data;

/**
 * @author jipengfei
 */
@Data
public class ExcelContentProperty {
    public static final ExcelContentProperty EMPTY = new ExcelContentProperty();

    /**
     * Java filed
     */
    private Field field;
    /**
     * Custom defined converters
     */
    private Converter<?> converter;
    /**
     * date time format
     */
    private DateTimeFormatProperty dateTimeFormatProperty;
    /**
     * number format
     */
    private NumberFormatProperty numberFormatProperty;
    /**
     * Content style
     */
    private StyleProperty contentStyleProperty;
    /**
     * Content font
     */
    private FontProperty contentFontProperty;
}
