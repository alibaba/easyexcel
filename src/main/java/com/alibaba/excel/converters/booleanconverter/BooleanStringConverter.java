package com.alibaba.excel.converters.booleanconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Boolean and string converter
 *
 * @author zhuangjiaju
 */
public class BooleanStringConverter implements Converter<Boolean> {

    @Override
    public Class supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Boolean.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Boolean value, ExcelContentProperty contentProperty) {
        return new CellData(value.toString());
    }

}
