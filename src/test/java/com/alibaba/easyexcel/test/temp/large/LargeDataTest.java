package com.alibaba.easyexcel.test.temp.large;

import java.io.FileInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.MapCache;

/**
 *
 * @author Jiaju Zhuang
 */
public class LargeDataTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeDataTest.class);

    @Test
    public void read() throws Exception {
        long start = System.currentTimeMillis();
        EasyExcel.read(new FileInputStream("D:\\test\\MRP生产视图(1).xlsx"), LargeData.class, new LargeDataListener()).readCache(new eh)
            .headRowNumber(2).sheet().doRead();
        LOGGER.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }
}
