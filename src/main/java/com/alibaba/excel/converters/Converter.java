package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Convert between Java objects and excel objects
 *
 * @author Dan Zheng
 * @param <T>
 */
public interface Converter<T> {

    /**
     * Back to object types in Java
     *
     * @return
     */
    Class supportJavaTypeKey();

    /**
     * Back to object enum in excel
     *
     * @return
     */
    CellDataTypeEnum supportExcelTypeKey();

    /**
     * Convert excel objects to Java objects
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    T convertToJavaData(@NotNull CellData cellData, @Nullable ExcelContentProperty contentProperty,
        @NotNull GlobalConfiguration globalConfiguration) throws Exception;

    /**
     * Convert Java objects to excel objects
     *
     * @param value
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    CellData convertToExcelData(@NotNull T value, @Nullable ExcelContentProperty contentProperty,
        @NotNull GlobalConfiguration globalConfiguration) throws Exception;
}
