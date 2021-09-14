package com.alibaba.excel.converters.bigdecimal;

import java.math.BigDecimal;
import java.text.ParseException;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * BigDecimal and string converter
 *
 * @author Jiaju Zhuang
 */
public class BigDecimalStringConverter implements Converter<BigDecimal> {

    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseBigDecimal(cellData.getStringValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }
}
