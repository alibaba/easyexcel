package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

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
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     * @throws Exception
     */
    T convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception;

    /**
     * Convert Java objects to excel objects
     *
     * @param value
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     * @throws Exception
     */
    CellData convertToExcelData(T value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration)
        throws Exception;
}
