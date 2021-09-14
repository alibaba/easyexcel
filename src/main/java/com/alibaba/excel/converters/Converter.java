package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

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
    default T convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        throw new UnsupportedOperationException("The current operation is not supported by the current converter.");
    }

    /**
     * Convert excel objects to Java objects
     *
     * @param context read converter context
     * @return Data to put into a Java object
     * @throws Exception Exception.
     */
    default T convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return convertToJavaData(context.getReadCellData(), context.getContentProperty(),
            context.getAnalysisContext().currentReadHolder().globalConfiguration());
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
    default WriteCellData<?> convertToExcelData(T value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        throw new UnsupportedOperationException("The current operation is not supported by the current converter.");
    }

    /**
     * Convert Java objects to excel objects
     *
     * @param context write context
     * @return Data to put into a Excel
     * @throws Exception Exception.
     */
    default WriteCellData<?> convertToExcelData(WriteConverterContext<T> context) throws Exception {
        return convertToExcelData(context.getValue(), context.getContentProperty(),
            context.getWriteContext().currentWriteHolder().globalConfiguration());
    }
}
