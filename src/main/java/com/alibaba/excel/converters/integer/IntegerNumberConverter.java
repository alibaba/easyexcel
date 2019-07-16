package com.alibaba.excel.converters.integer;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Integer and number converter
 *
 * @author zhuangjiaju
 */
public class IntegerNumberConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return cellData.getDoubleValue().intValue();
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty) {
        return new CellData(value.doubleValue());
    }

}
