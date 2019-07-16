package com.alibaba.excel.converters.byteconverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Byte and string converter
 *
 * @author zhuangjiaju
 */
public class ByteStringConverter implements Converter<Byte> {

    @Override
    public Class supportJavaTypeKey() {
        return Byte.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Byte convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        return Byte.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Byte value, ExcelContentProperty contentProperty) {
        return new CellData(value.toString());
    }

}
