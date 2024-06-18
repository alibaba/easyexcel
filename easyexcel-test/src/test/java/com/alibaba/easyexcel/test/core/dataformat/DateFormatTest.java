package com.alibaba.easyexcel.test.core.dataformat;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
@Slf4j
public class DateFormatTest {

    private static File file07;
    private static File file03;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xlsx");
        file03 = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xls");
    }

    @Test
    public void t01Read07() {
        readCn(file07);
        readUs(file07);
    }

    @Test
    public void t02Read03() {
        readCn(file03);
        readUs(file03);
    }

    private void readCn(File file) {
        List<DateFormatData> list =
            EasyExcel.read(file, DateFormatData.class, null).locale(Locale.CHINA).sheet().doReadSync();
        for (DateFormatData data : list) {
            if (!Objects.equals(data.getDateStringCn(), data.getDate()) && !Objects.equals(data.getDateStringCn2(),
                data.getDate())) {
                log.info("date:cn:{},{},{}", data.getDateStringCn(), data.getDateStringCn2(), data.getDate());
            }
            if (data.getNumberStringCn() != null && !data.getNumberStringCn().equals(data.getNumber())) {
                log.info("number:cn{},{}", data.getNumberStringCn(), data.getNumber());
            }
        }
        for (DateFormatData data : list) {
            // The way dates are read in Chinese is different on Linux and Mac, so it is acceptable if it matches
            // either one.
            // For example, on Linux: 1-Jan -> 1-1月
            // On Mac: 1-Jan -> 1-一月
            Assertions.assertTrue(
                Objects.equals(data.getDateStringCn(), data.getDate()) || Objects.equals(data.getDateStringCn2(),
                    data.getDate()));
            Assertions.assertEquals(data.getNumberStringCn(), data.getNumber());
        }
    }

    private void readUs(File file) {
        List<DateFormatData> list =
            EasyExcel.read(file, DateFormatData.class, null).locale(Locale.US).sheet().doReadSync();
        for (DateFormatData data : list) {
            if (data.getDateStringUs() != null && !data.getDateStringUs().equals(data.getDate())) {
                log.info("date:us:{},{}", data.getDateStringUs(), data.getDate());
            }
            if (data.getNumberStringUs() != null && !data.getNumberStringUs().equals(data.getNumber())) {
                log.info("number:us{},{}", data.getNumberStringUs(), data.getNumber());
            }
        }
        for (DateFormatData data : list) {
            Assertions.assertEquals(data.getDateStringUs(), data.getDate());
            Assertions.assertEquals(data.getNumberStringUs(), data.getNumber());
        }
    }
}
