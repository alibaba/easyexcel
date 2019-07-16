package com.alibaba.excel.converters.doubleconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Double and string converter
 *
 * @author zhuangjiaju
 */
public class DoubleStringConverter implements Converter<Double> {

    @Override
    public Class supportJavaTypeKey() {
        return Double.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Double convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Double.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Double value, ExcelContentProperty contentProperty) {
        return new CellData(value.toString());
    }
}
