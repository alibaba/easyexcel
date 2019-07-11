package com.alibaba.excel.converters.string;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;

/**
 * String and string converter
 *
 * @author zhuangjiaju
 */
public class StringStringConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) {
        return cellData.getStringValue();
    }

    @Override
    public CellData convertToExcelData(String value, ExcelColumnProperty columnProperty) {
        return new CellData(value);
    }

}
