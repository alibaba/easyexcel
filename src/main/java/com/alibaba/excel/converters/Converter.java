package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * Convert between Java objects and excel objects
 *
 * @param <T>
 * @author Dan Zheng
 */
public interface Converter<T> {

    /**
     * Back to object types in Java
     *
     * @return Support for Java class
     */
    default Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("The current operation is not supported by the current converter.");
    }

    /**
     * Back to object enum in excel
     *
     * @return Support for {@link CellDataTypeEnum}
     */
    default CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("The current operation is not supported by the current converter.");
    }

    /**
     * Convert excel objects to Java objects
     *
     * @param cellData            Excel cell data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Java object
     * @throws Exception Exception.
     */
    default T convertToJavaData(CellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        throw new UnsupportedOperationException("The current operation is not supported by the current converter.");
    }

    /**
     * Convert excel objects to Java objects
     *
     * @param cellData        Excel cell data.NotNull.
     * @param contentProperty Content property.Nullable.
     * @param readSheetHolder .NotNull.
     * @return Data to put into a Java object
     * @throws Exception Exception.
     */
    default T convertToJavaData(CellData<?> cellData,
        ExcelContentProperty contentProperty, ReadSheetHolder readSheetHolder) throws Exception {
        return convertToJavaData(cellData, contentProperty, readSheetHolder.globalConfiguration());
    }

    /**
     * Convert Java objects to excel objects
     *
     * @param value               Java Data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Excel
     * @throws Exception Exception.
     */
    default CellData<?> convertToExcelData(T value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        throw new UnsupportedOperationException("The current operation is not supported by the current converter.");
    }

    /**
     * Convert Java objects to excel objects
     *
     * @param value              Java Data.NotNull.
     * @param contentProperty    Content property.Nullable.
     * @param currentWriteHolder He would be {@link WriteSheetHolder} or  {@link WriteTableHolder}.NotNull.
     * @return Data to put into a Excel
     * @throws Exception Exception.
     */
    default CellData<?> convertToExcelData(T value, ExcelContentProperty contentProperty,
        WriteHolder currentWriteHolder) throws Exception {
        return convertToExcelData(value, contentProperty, currentWriteHolder.globalConfiguration());
    }
}
