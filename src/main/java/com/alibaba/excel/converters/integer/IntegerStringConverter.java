package com.alibaba.excel.converters.integer;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * Integer and string converter
 *
 * @author zhuangjiaju
 */
public class IntegerStringConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Integer.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty) {
        return NumberUtils.formatToCellData(value, contentProperty);
    }
}
