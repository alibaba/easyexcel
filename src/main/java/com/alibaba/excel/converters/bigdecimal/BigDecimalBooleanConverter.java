package com.alibaba.excel.converters.bigdecimal;

import java.math.BigDecimal;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * BigDecimal and boolean converter
 *
 * @author Jiaju Zhuang
 */
public class BigDecimalBooleanConverter implements Converter<BigDecimal> {

    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        if (cellData.getBooleanValue()) {
            return BigDecimal.ONE;
        }
        return BigDecimal.ZERO;
    }

    @Override
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        if (BigDecimal.ONE.equals(value)) {
            return new WriteCellData<>(Boolean.TRUE);
        }
        return new WriteCellData<>(Boolean.FALSE);
    }

}
