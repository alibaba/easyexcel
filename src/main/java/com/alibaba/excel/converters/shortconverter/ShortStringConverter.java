package com.alibaba.excel.converters.shortconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * Short and string converter
 *
 * @author zhuangjiaju
 */
public class ShortStringConverter implements Converter<Short> {

    @Override
    public Class supportJavaTypeKey() {
        return Short.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Short convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Short.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Short value, ExcelContentProperty contentProperty) {
        return NumberUtils.formatToCellData(value, contentProperty);
    }
}
