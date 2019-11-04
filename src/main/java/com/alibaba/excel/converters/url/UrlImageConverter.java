package com.alibaba.excel.converters.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;

/**
 * Url and image converter
 *
 * @since 2.1.1
 * @author Jiaju Zhuang
 */
public class UrlImageConverter implements Converter<URL> {
    @Override
    public Class supportJavaTypeKey() {
        return URL.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.IMAGE;
    }

    @Override
    public URL convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        throw new UnsupportedOperationException("Cannot convert images to url.");
    }

    @Override
    public CellData convertToExcelData(URL value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = value.openStream();
            byte[] bytes = IoUtils.toByteArray(inputStream);
            return new CellData(bytes);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
