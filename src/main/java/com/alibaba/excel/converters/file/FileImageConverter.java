package com.alibaba.excel.converters.file;

import java.io.File;
import java.io.IOException;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

/**
 * File and image converter
 *
 * @author Jiaju Zhuang
 */
public class FileImageConverter implements Converter<File> {
    @Override
    public Class supportJavaTypeKey() {
        return File.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.IMAGE;
    }

    @Override
    public File convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        throw new UnsupportedOperationException("Cannot convert images to file");
    }

    @Override
    public CellData convertToExcelData(File value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws IOException {
        return new CellData(FileUtils.readFileToByteArray(value));
    }

}
