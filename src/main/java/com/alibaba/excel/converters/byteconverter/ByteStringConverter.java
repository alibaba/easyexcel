package com.alibaba.excel.converters.byteconverter;

import java.text.ParseException;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;

/**
 * Byte and string converter
 *
 * @author Jiaju Zhuang
 */
public class ByteStringConverter implements Converter<Byte> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Byte.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Byte convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseByte(cellData.getStringValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Byte value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }

}
