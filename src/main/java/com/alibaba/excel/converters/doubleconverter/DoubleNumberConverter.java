package com.alibaba.excel.converters.doubleconverter;

import java.math.BigDecimal;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

/**
 * Double and number converter
 *
 * @author Jiaju Zhuang
 */
public class DoubleNumberConverter implements Converter<Double> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Double.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Double convertToJavaData(CellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().doubleValue();
    }

    @Override
    public CellData<?> convertToExcelData(Double value, ExcelContentProperty contentProperty,
        WriteHolder currentWriteHolder) {
        return NumberUtils.formatToCellData(value, contentProperty, currentWriteHolder);
    }
}
