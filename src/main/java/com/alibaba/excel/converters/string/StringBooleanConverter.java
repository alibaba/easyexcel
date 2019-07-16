package com.alibaba.excel.converters.string;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * String and boolean converter
 *
 * @author zhuangjiaju
 */
public class StringBooleanConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return cellData.getBooleanValue().toString();
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty) {
        return new CellData(Boolean.valueOf(value));
    }

}
