package com.alibaba.excel.converters.booleanconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Boolean and boolean converter
 *
 * @author Jiaju Zhuang
 */
public class BooleanBooleanConverter implements Converter<Boolean> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getBooleanValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value);
    }

}
