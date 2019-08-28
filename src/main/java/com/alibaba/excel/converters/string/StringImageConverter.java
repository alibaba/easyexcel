package com.alibaba.excel.converters.string;

import java.io.File;
import java.io.IOException;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

/**
 * String and image converter
 *
 * @author Jiaju Zhuang
 */
public class StringImageConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.IMAGE;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        throw new UnsupportedOperationException("Cannot convert images to string");
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws IOException {
        return new CellData(FileUtils.readFileToByteArray(new File(value)));
    }

}
