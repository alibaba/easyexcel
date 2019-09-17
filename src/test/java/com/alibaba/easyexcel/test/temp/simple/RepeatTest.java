package com.alibaba.easyexcel.test.temp.simple;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.temp.LockData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class RepeatTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepeatTest.class);

    @Test
    public void hh() throws IOException {
        ExcelReader reader =
            EasyExcel.read(new FileInputStream("D:\\test\\hg2.xls"), LockData.class, new RepeatListener())
                .headRowNumber(0).build();
        ReadSheet r1 = EasyExcel.readSheet(0).build();
        ReadSheet r2 = EasyExcel.readSheet(2).build();
        reader.read(r1);
        reader.read(r2);
        reader.finish();
    }

    @Test
    public void hh2() throws IOException {
        ExcelReader reader =
            EasyExcel.read(new FileInputStream("D:\\test\\sheet.xls"), LockData.class, new RepeatListener())
                .headRowNumber(0).build();
        ReadSheet r2 = EasyExcel.readSheet(1).build();
        reader.read(r2);
        reader.finish();
    }

    @Test
    public void hh1() throws IOException {
        ExcelReader reader =
            EasyExcel.read(new FileInputStream("D:\\test\\hg2.xls"), LockData.class, new RepeatListener())
                .headRowNumber(0).build();
        ReadSheet r2 = EasyExcel.readSheet(0).build();
        reader.read(r2);
        reader.finish();
    }
}
