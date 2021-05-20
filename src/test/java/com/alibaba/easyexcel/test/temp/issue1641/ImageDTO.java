package com.alibaba.easyexcel.test.temp.issue1641;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.inputstream.InputStreamImageConverter;
import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

@Data
//@ColumnWidth(100 / 8)
//@ContentRowHeight(100)
public class ImageDTO implements Serializable {
    private static final long serialVersionUID = -4691788793083711462L;

    @ExcelProperty(index = 0, converter = InputStreamImageConverter.class)
    private InputStream workerImg;
}
