package com.alibaba.excel.converters.longconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * Long and string converter
 *
 * @author zhuangjiaju
 */
public class LongStringConverter implements Converter<Long> {

    @Override
    public Class supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Long convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Long.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Long value, ExcelContentProperty contentProperty) {
        return NumberUtils.formatToCellData(value, contentProperty);
    }
}
