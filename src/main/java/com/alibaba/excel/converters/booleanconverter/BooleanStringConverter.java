package com.alibaba.excel.converters.booleanconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;

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
    public Boolean convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) {
        return Boolean.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Boolean value, ExcelColumnProperty columnProperty) {
        return new CellData(value.toString());
    }

}
