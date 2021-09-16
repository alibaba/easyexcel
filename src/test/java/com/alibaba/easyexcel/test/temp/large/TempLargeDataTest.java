package com.alibaba.easyexcel.test.temp.large;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;

import com.alibaba.easyexcel.test.core.large.LargeDataTest;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
@Ignore
public class TempLargeDataTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LargeDataTest.class);

    @Test
    public void read() throws Exception {
        long start = System.currentTimeMillis();
        EasyExcel.read(new FileInputStream("D:\\test\\MRP生产视图(1).xlsx"), LargeData.class, new LargeDataListener())
            .headRowNumber(2).sheet().doRead();
        LOGGER.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void noModelRead() throws Exception {
        ZipSecureFile.setMaxEntrySize(Long.MAX_VALUE);
        long start = System.currentTimeMillis();
        EasyExcel.read(TestFileUtil.readUserHomeFile("test/ld.xlsx"), new NoModelLargeDataListener())
            .sheet().doRead();
        LOGGER.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void noModelRead2() throws Exception {
        Field field = ZipSecureFile.class.getDeclaredField("MAX_ENTRY_SIZE");
        field.setAccessible(true);
        field.set(null, Long.MAX_VALUE);

        long start = System.currentTimeMillis();
        EasyExcel.read(
            new File("/Users/zhuangjiaju/IdeaProjects/easyexcel/target/test-classes/large1617887262709.xlsx"),
            new NoModelLargeDataListener())
            .sheet().doRead();
        LOGGER.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }
}
