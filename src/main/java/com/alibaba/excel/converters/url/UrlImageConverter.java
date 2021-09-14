package com.alibaba.excel.converters.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;

/**
 * Url and image converter
 *
 * @author Jiaju Zhuang
 * @since 2.1.1
 */
public class UrlImageConverter implements Converter<URL> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return URL.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(URL value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws IOException {
        InputStream inputStream = null;
        try {
            URLConnection conn = value.openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(5000);
            inputStream = con.getInputStream();
            byte[] bytes = IoUtils.toByteArray(inputStream);    
            return new WriteCellData<>(bytes);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
