package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
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
     * @param columnProperty
     * @return
     * @throws Exception
     */
    T convertToJavaData(@NotNull CellData cellData, @Nullable ExcelColumnProperty columnProperty) throws Exception;

    /**
     * Convert Java objects to excel objects
     * 
     * @param value
     * @param columnProperty
     * @return
     * @throws Exception
     */
    CellData convertToExcelData(@NotNull T value, @Nullable ExcelColumnProperty columnProperty) throws Exception;
}
