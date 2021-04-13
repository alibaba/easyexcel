package com.alibaba.excel.converters.shortconverter;

import java.math.BigDecimal;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

/**
 * Short and number converter
 *
 * @author Jiaju Zhuang
 */
public class ShortNumberConverter implements Converter<Short> {

    @Override
    public Class<Short> supportJavaTypeKey() {
        return Short.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Short convertToJavaData(CellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().shortValue();
    }

    @Override
    public CellData<?> convertToExcelData(Short value, ExcelContentProperty contentProperty,
        WriteHolder currentWriteHolder) {
        return NumberUtils.formatToCellData(value, contentProperty, currentWriteHolder);
    }
}
