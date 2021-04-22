package com.alibaba.excel.converters.longconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * Long and number converter
 *
 * @author Jiaju Zhuang
 */
public class LongNumberConverter implements Converter<Long> {

    @Override
    public Class<Long> supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().longValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Long> context) {
        return NumberUtils.formatToCellData(context.getValue(), context.getContentProperty());
    }

}
