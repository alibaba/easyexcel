package com.alibaba.excel.converters.floatconverter;

import java.math.BigDecimal;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Float and number converter
 *
 * @author Jiaju Zhuang
 */
public class FloatNumberConverter implements Converter<Float> {

    @Override
    public Class supportJavaTypeKey() {
        return Float.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Float convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().floatValue();
    }

    @Override
    public CellData convertToExcelData(Float value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new CellData(BigDecimal.valueOf(value));
    }

}
