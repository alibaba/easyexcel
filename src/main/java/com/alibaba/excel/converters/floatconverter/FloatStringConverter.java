package com.alibaba.excel.converters.floatconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * Float and string converter
 *
 * @author zhuangjiaju
 */
public class FloatStringConverter implements Converter<Float> {

    @Override
    public Class supportJavaTypeKey() {
        return Float.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Float convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Float.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Float value, ExcelContentProperty contentProperty) {
        return NumberUtils.formatToCellData(value, contentProperty);
    }
}
