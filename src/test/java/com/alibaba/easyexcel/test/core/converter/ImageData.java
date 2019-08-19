package com.alibaba.easyexcel.test.core.converter;

import java.io.File;
import java.io.InputStream;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
@ContentRowHeight(500)
@ColumnWidth(500 / 8)
public class ImageData {
    private File file;
    private InputStream inputStream;
    @ExcelProperty(converter = StringImageConverter.class)
    private String string;
    private byte[] byteArray;
}
