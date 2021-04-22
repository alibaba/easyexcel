package com.alibaba.excel.converters.shortconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

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
    public Short convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().shortValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Short> context) {
        return NumberUtils.formatToCellData(context.getValue(), context.getContentProperty());
    }
}
