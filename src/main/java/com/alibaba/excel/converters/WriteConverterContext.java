package com.alibaba.excel.converters;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import lombok.Data;

/**
 * write converter context
 *
 * @author Jiaju Zhuang
 */
@Data
@AllArgsConstructor
public class WriteConverterContext<T> {

    /**
     * Java Data.NotNull.
     */
    private T value;

    /**
     * Content property.Nullable.
     */
    private ExcelContentProperty contentProperty;

    /**
     * write context
     */
    private WriteContext writeContext;
}
