package com.alibaba.easyexcel.test.temp.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Pattern;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.fastjson.JSON;


/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
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
        sxxsFWorkbook.write(fileOutputStream);
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
    public void test() throws Exception {
        Class<TestCell> clazz = TestCell.class;

        Field field = clazz.getDeclaredField("c2");
        // 通过getDeclaredField可以获得成员变量，但是对于Map来说，仅仅可以知道它是个Map，无法知道键值对各自的数据类型

        Type gType = field.getGenericType();
        // 获得field的泛型类型

        // 如果gType是ParameterizedType对象（参数化）
        if (gType instanceof ParameterizedType) {

            ParameterizedType pType = (ParameterizedType)gType;
            // 就把它转换成ParameterizedType对象

            Type[] tArgs = pType.getActualTypeArguments();
            // 获得泛型类型的泛型参数（实际类型参数)
            ParameterizedTypeImpl c = (ParameterizedTypeImpl)pType.getActualTypeArguments()[0];
            Class ttt = c.getClass();
            System.out.println(ttt);
        } else {
            System.out.println("出错！！！");
        }

    }

    @Test
    public void test2() throws Exception {
        Class<TestCell> clazz = TestCell.class;

        Field field = clazz.getDeclaredField("c2");
        // 通过getDeclaredField可以获得成员变量，但是对于Map来说，仅仅可以知道它是个Map，无法知道键值对各自的数据类型

        Type gType = field.getGenericType();
        // 获得field的泛型类型

        // 如果gType是ParameterizedType对象（参数化）
        if (gType instanceof ParameterizedType) {

            ParameterizedType pType = (ParameterizedType)gType;
            // 就把它转换成ParameterizedType对象

            Type[] tArgs = pType.getActualTypeArguments();
            // 获得泛型类型的泛型参数（实际类型参数)
            ParameterizedTypeImpl c = (ParameterizedTypeImpl)pType.getActualTypeArguments()[0];
            Class ttt = c.getClass();
            System.out.println(ttt);
        } else {
            System.out.println("出错！！！");
        }

    }

}
