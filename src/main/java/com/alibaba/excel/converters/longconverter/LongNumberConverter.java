package com.alibaba.excel.converters.longconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Long and number converter
 *
 * @author zhuangjiaju
 */
public class LongNumberConverter implements Converter<Long> {

    @Override
    public Class supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Long convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return cellData.getDoubleValue().longValue();
    }

    @Override
    public CellData convertToExcelData(Long value, ExcelContentProperty contentProperty) {
        return new CellData(value.doubleValue());
    }

}
