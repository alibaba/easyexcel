package com.alibaba.easyexcel.test.core.dataformat;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

/**
 *
 * @author Jiaju Zhuang
 */
public class DateFormatTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatTest.class);

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xlsx");
        file03 = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xls");
    }

    @Test
    public void t01Read07() {
        read(file07);
    }

    @Test
    public void t02Read03() {
        read(file03);
    }

    private void read(File file) {
        List<DateFormatData> list = EasyExcel.read(file, DateFormatData.class, null).sheet().doReadSync();
        for (DateFormatData data : list) {
            if (!data.getDate().equals(data.getDateString())) {
                LOGGER.info("返回:{}", JSON.toJSONString(data));
            }
        }
    }

}
