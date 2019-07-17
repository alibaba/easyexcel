package com.alibaba.easyexcel.test.read.large;

import java.io.InputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.FileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;

/**
 * Simple data test
 * 
 * @author zhuangjiaju
 */
public class LargeData07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeData07Test.class);

    @Test
    public void large() throws Exception {
        LOGGER.info("start");
        long start = System.currentTimeMillis();
        InputStream inputStream = FileUtil.readFile("large/large07.xlsx");
        ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, new LargeDataListener());
        excelReader.read(new Sheet(1, 1));
        inputStream.close();
        LOGGER.info("time:{}", System.currentTimeMillis() - start);
    }

}
