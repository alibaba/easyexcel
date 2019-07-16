package com.alibaba.excel.converters.shortconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Short and number converter
 *
 * @author zhuangjiaju
 */
public class ShortNumberConverter implements Converter<Short> {

    @Override
    public Class supportJavaTypeKey() {
        return Short.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Short convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return cellData.getDoubleValue().shortValue();
    }

    @Override
    public CellData convertToExcelData(Short value, ExcelContentProperty contentProperty) {
        return new CellData(value.doubleValue());
    }

}
