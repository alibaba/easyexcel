package com.alibaba.excel.converters.booleanconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Boolean and number converter
 *
 * @author zhuangjiaju
 */
public class BooleanNumberConverter implements Converter<Boolean> {

    private static final Double ONE = 1.0;
    private static final Double ZERO = 0.0;

    @Override
    public Class supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        if (ONE.equals(cellData.getDoubleValue())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public CellData convertToExcelData(Boolean value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        if (value) {
            return new CellData(ONE);
        }
        return new CellData(ZERO);
    }

}
