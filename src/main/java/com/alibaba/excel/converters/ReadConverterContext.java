package com.alibaba.excel.converters;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * read converter context
 *
 * @author Jiaju Zhuang
 */
@Data
@AllArgsConstructor
public class ReadConverterContext<T> {
    /**
     * Excel cell data.NotNull.
     */
    private ReadCellData<T> readCellData;
    /**
     * Content property.Nullable.
     */
    private ExcelContentProperty contentProperty;
    /**
     * context.NotNull.
     */
    private AnalysisContext analysisContext;
}
