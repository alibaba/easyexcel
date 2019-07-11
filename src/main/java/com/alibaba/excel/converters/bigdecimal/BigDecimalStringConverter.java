package com.alibaba.excel.converters.bigdecimal;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.util.StringUtils;

/**
 * BigDecimal and string converter
 *
 * @author zhuangjiaju
 */
public class BigDecimalStringConverter implements Converter<BigDecimal> {

    @Override
    public Class supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigDecimal convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) {
        return new BigDecimal(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(BigDecimal value, ExcelColumnProperty columnProperty) {
        if (StringUtils.isEmpty(columnProperty.getFormat())) {
            return new CellData(value.toString());
        }
        return new CellData(new DecimalFormat(columnProperty.getFormat()).format(value));
    }
}
