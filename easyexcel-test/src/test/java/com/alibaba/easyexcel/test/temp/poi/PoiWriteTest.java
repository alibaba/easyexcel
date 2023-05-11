package com.alibaba.easyexcel.test.temp.poi;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.regex.Pattern;

import com.alibaba.fastjson2.JSON;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/

public class PoiWriteTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiWriteTest.class);

    @Test
    public void write0() throws IOException {
        FileOutputStream fileOutputStream =
            new FileOutputStream("D://test//tt132" + System.currentTimeMillis() + ".xlsx");
        SXSSFWorkbook sxxsFWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxxsFWorkbook.createSheet("t1");
        SXSSFRow row = sheet.createRow(0);
        SXSSFCell cell1 = row.createCell(0);
        cell1.setCellValue(999999999999999L);
        SXSSFCell cell2 = row.createCell(1);
        cell2.setCellValue(1000000000000001L);
        SXSSFCell cell32 = row.createCell(2);
        cell32.setCellValue(300.35f);
        sxxsFWorkbook.write(fileOutputStream);
    }

    @Test
    public void write01() throws IOException {
        float ff = 300.35f;
        BigDecimal bd = new BigDecimal(Float.toString(ff));
        System.out.println(bd.doubleValue());
        System.out.println(bd.floatValue());

    }

    @Test
    public void write() throws IOException {
        FileOutputStream fileOutputStream =
            new FileOutputStream("D://test//tt132" + System.currentTimeMillis() + ".xlsx");
        SXSSFWorkbook sxxsFWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxxsFWorkbook.createSheet("t1");
        SXSSFRow row = sheet.createRow(0);
        SXSSFCell cell1 = row.createCell(0);
        cell1.setCellValue(Long.toString(999999999999999L));
        SXSSFCell cell2 = row.createCell(1);
        cell2.setCellValue(Long.toString(1000000000000001L));
        sxxsFWorkbook.write(fileOutputStream);
    }

    @Test
    public void write1() throws IOException {
        System.out.println(JSON.toJSONString(long2Bytes(-999999999999999L)));
        System.out.println(JSON.toJSONString(long2Bytes(-9999999999999999L)));
    }

    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte)((num >> offset) & 0xff);
        }
        return byteNum;
    }

    private static final Pattern FILL_PATTERN = Pattern.compile("^.*?\\$\\{[^}]+}.*?$");

    @Test
    public void part() throws IOException {
        LOGGER.info("test:{}", FILL_PATTERN.matcher("${name今年${number}岁了").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("${name}今年${number}岁了").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("${name}").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("${number}").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("${name}今年").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("今年${number}岁了").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("今年${number岁了").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("${}").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("胜多负少").matches());
    }

    private static final Pattern FILL_PATTERN2 = Pattern.compile("测试");

    @Test
    public void part2() throws IOException {
        LOGGER.info("test:{}", FILL_PATTERN.matcher("我是测试呀").find());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("测试u").matches());
        LOGGER.info("test:{}", FILL_PATTERN.matcher("我是测试").matches());

    }

    @Test
    public void part4() throws IOException {
        //URL url=new URL("http://120.55.161.4/group1/M00/00/00/i8QJ8WFfwMiAXKYrAAACqC1MFiY641.png");
        URL url = new URL(
            "https://img-blog.csdn.net/20160729002743309?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T"
                + "/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center");

        InputStream in = new BufferedInputStream(url.openStream());

    }

}
