package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.Head;

import lombok.Data;

/**
 * @author jipengfei
 */
@Data
public class ExcelContentProperty {
    /**
     * Java filed
     */
    private Field field;
    /**
     * Excel head
     */
    private Head head;
    /**
     * Custom defined converters
     */
    private Converter<?> converter;
    private DateTimeFormatProperty dateTimeFormatProperty;
    private NumberFormatProperty numberFormatProperty;
}
