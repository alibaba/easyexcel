package com.alibaba.excel.converters.longconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Long and boolean converter
 *
 * @author zhuangjiaju
 */
public class LongBooleanConverter implements Converter<Long> {
    private static final Long ONE = 1L;
    private static final Long ZERO = 0L;

    @Override
    public Class supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    @Override
    public Long convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        if (cellData.getBooleanValue()) {
            return ONE;
        }
        return ZERO;
    }

    @Override
    public CellData convertToExcelData(Long value, ExcelContentProperty contentProperty) {
        if (ONE.equals(value)) {
            return new CellData(Boolean.TRUE);
        }
        return new CellData(Boolean.FALSE);
    }

}
