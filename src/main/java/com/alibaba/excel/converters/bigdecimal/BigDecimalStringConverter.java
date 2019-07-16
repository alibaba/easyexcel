package com.alibaba.excel.converters.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
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
    public BigDecimal convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return new BigDecimal(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty) {
        String format = null;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        if (contentProperty.getNumberFormatProperty() != null) {
            format = contentProperty.getNumberFormatProperty().getFormat();
            roundingMode = contentProperty.getNumberFormatProperty().getRoundingMode();
        }
        if (StringUtils.isEmpty(format)) {
            return new CellData(value.toString());
        }
        DecimalFormat decimalFormat = new DecimalFormat(format);
        decimalFormat.setRoundingMode(roundingMode);
        return new CellData(decimalFormat.format(value));
    }
}
