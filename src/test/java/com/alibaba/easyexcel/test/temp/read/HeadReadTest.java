package com.alibaba.easyexcel.test.temp.read;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.cache.Ehcache;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class HeadReadTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeadReadTest.class);
    @Test
    public void testread() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D://test/t1.xlsx");

        ExcelReader excelReader = new ExcelReader(fileInputStream, ExcelTypeEnum.XLSX, null, new TestListener());
        excelReader.read();
    }

    @Test
    public void test() throws Exception {
        File file = TestFileUtil.readUserHomeFile("test/t2.xlsx");
        EasyExcel.read(file, HeadReadData.class, new HeadListener()).ignoreEmptyRow(false).sheet(0).doRead();

    }

    @Test
    public void testCache() throws Exception {
        File file = new File("D:\\test\\headt1.xls");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).readCache(new Ehcache(20)).sheet(0).doRead();

        LOGGER.info("------------------");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).readCache(new Ehcache(20)).sheet(0).doRead();
        LOGGER.info("------------------");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).readCache(new Ehcache(20)).sheet(0).doRead();
    }

}
