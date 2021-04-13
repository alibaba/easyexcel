package com.alibaba.excel.converters.bytearray;

import java.lang.annotation.Annotation;

import com.alibaba.excel.annotation.write.style.ImagePosition;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Byte array and image converter
 *
 * @author Jiaju Zhuang
 */
public class ByteArrayImageConverter implements Converter<byte[]> {
    @Override
    public Class<byte[]> supportJavaTypeKey() {
        return byte[].class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.IMAGE;
    }

    @Override

    public byte[] convertToJavaData(CellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        throw new UnsupportedOperationException("Cannot convert images to byte arrays");
    }

    @Override
    public CellData convertToExcelData(byte[] value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        ImagePosition imagePosition = contentProperty.getField().getAnnotation(ImagePosition.class);
        if (imagePosition != null) {
            return new CellData(value, imagePosition);
        } else {
            return new CellData(value);
        }
    }

}
