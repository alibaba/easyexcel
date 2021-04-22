package com.alibaba.excel.converters.file;

import java.io.File;
import java.io.IOException;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

/**
 * File and image converter
 *
 * @author Jiaju Zhuang
 */
public class FileImageConverter implements Converter<File> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return File.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(File value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws IOException {
        return new WriteCellData<>(FileUtils.readFileToByteArray(value));
    }
}
