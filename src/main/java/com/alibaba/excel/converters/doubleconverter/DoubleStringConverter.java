package com.alibaba.excel.converters.doubleconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;

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
    public Double convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) {
        return Double.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Double value, ExcelColumnProperty columnProperty) {
        return new CellData(value.toString());
    }
}
