package com.alibaba.excel.converters.bigdecimal;

import java.math.BigDecimal;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;

/**
 * BigDecimal and number converter
 *
 * @author zhuangjiaju
 */
public class BigDecimalNumberConverter implements Converter<BigDecimal> {

    @Override
    public Class supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public BigDecimal convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) {
        return new BigDecimal(cellData.getDoubleValue());
    }

    @Override
    public CellData convertToExcelData(BigDecimal value, ExcelColumnProperty columnProperty) {
        return new CellData(value.doubleValue());
    }
}
