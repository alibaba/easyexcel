package com.alibaba.excel.converters.booleanconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Boolean and boolean converter
 *
 * @author zhuangjiaju
 */
public class BooleanBooleanConverter implements Converter<Boolean> {

    @Override
    public Class supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return cellData.getBooleanValue();
    }

    @Override
    public CellData convertToExcelData(Boolean value, ExcelContentProperty contentProperty) {
        return new CellData(value);
    }

}
