package com.alibaba.easyexcel.test.read.simple;

import java.io.InputStream;

import org.junit.Test;

import com.alibaba.easyexcel.test.util.FileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;

/**
 * Simple data test
 * 
 * @author zhuangjiaju
 */
public class SimpleData07Test {

    @Test
    public void simple() throws Exception {
        InputStream inputStream = FileUtil.readFile("simple/simple07.xlsx");
        ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, new SimpleDataListener());
        excelReader.read(new Sheet(1, 1), SimpleData.class);
        inputStream.close();
    }
}
