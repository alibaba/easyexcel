package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public interface Converter<T> {

    Class supportJavaTypeKey();

    CellDataTypeEnum supportExcelTypeKey();

    T convertToJavaData(@NotNull CellData cellData, @Nullable ExcelColumnProperty columnProperty) throws Exception;

    CellData convertToExcelData(@NotNull T value, @Nullable ExcelColumnProperty columnProperty) throws Exception;
}
