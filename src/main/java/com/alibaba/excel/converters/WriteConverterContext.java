package com.alibaba.excel.converters;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * write converter context
 *
 * @author Jiaju Zhuang
 */
@Data
@NoArgsConstructor
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
