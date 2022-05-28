package com.alibaba.easyexcel.test.core.dataformat;

import java.io.File;
import java.util.List;
import java.util.Locale;

import com.alibaba.excel.EasyExcel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class DateFormatTest {

    private static File file1;
    private static File file2;

    @BeforeClass
    public static void init() {
        file1 = new File("./test1.xlsx");
        file2 = new File("./test2.xlsx");
    }

    @Test
    public void testRead1() {
        List<DateFormatData> list =
            EasyExcel.read(file1, DateFormatData.class, null).locale(Locale.CHINA).sheet().doReadSync();
        for (DateFormatData data : list) {
            if (data.getDateStringCn() != null && !data.getDateStringCn().equals(data.getDate())) {
                log.info("date:cn:{},{}", data.getDateStringCn(), data.getDate());
            }
            if (data.getNumberStringCn() != null && !data.getNumberStringCn().equals(data.getNumber())) {
                log.info("number:cn{},{}", data.getNumberStringCn(), data.getNumber());
            }
        }
        for (DateFormatData data : list) {
            Assert.assertEquals(data.getDateStringCn(), data.getDate());
            Assert.assertEquals(data.getNumberStringCn(), data.getNumber());
        }
    }

    @Test
    public void testRead2() {
        List<DateFormatData> list =
            EasyExcel.read(file2, DateFormatData.class, null).locale(Locale.CHINA).sheet().doReadSync();
        for (DateFormatData data : list) {
            if (data.getDateStringCn() != null && !data.getDateStringCn().equals(data.getDate())) {
                log.info("date:cn:{},{}", data.getDateStringCn(), data.getDate());
            }
            if (data.getNumberStringCn() != null && !data.getNumberStringCn().equals(data.getNumber())) {
                log.info("number:cn{},{}", data.getNumberStringCn(), data.getNumber());
            }
        }
        for (DateFormatData data : list) {
            Assert.assertEquals(data.getDateStringCn(), data.getDate());
            Assert.assertEquals(data.getNumberStringCn(), data.getNumber());
        }
    }

}
