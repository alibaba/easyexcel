package com.alibaba.easyexcel.test.temp.read;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
public class HeadReadTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeadReadTest.class);

    @Test
    public void test() throws Exception {
        File file = new File("D:\\test\\headt1.xlsx");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).sheet().doRead();
    }

}
