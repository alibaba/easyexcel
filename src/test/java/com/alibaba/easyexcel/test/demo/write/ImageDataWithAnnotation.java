package com.alibaba.easyexcel.test.demo.write;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ImagePosition;
import com.alibaba.excel.converters.string.StringImageConverter;

import lombok.Data;

/**
 * 图片导出类
 *
 */
@Data
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class ImageDataWithAnnotation {
    @ImagePosition(dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0, col1 = 0, row1 = 3, col2 = 1, row2 = 4)
    private File file;
    @ImagePosition(dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0, col1 = 0, row1 = 1, col2 = 2, row2 = 2)
    private InputStream inputStream;
    /**
     * 如果string类型 必须指定转换器，string默认转换成string
     */
    @ExcelProperty(converter = StringImageConverter.class)
    @ImagePosition(dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0, col1 = 2, row1 = 1, col2 = 3, row2 = 3)
    private String string;
    @ImagePosition(dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0, col1 = 3, row1 = 1, col2 = 4, row2 = 5)
    private byte[] byteArray;
    /**
     * 根据url导出
     *
     * @since 2.1.1
     */
    @ImagePosition(dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0, col1 = 4, row1 = 1, col2 = 5, row2 = 2)
    private URL url;
}
