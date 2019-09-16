package com.alibaba.easyexcel.test.temp.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class PoiWriteTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiWriteTest.class);

    @Test
    public void write() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D://test//tt12.xlsx");
        SXSSFWorkbook sxxsFWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxxsFWorkbook.createSheet("t1");
        SXSSFRow row = sheet.createRow(0);
        SXSSFCell cell1 = row.createCell(0);
        cell1.setCellValue(1);
        SXSSFCell cell2 = row.createCell(1);
        cell2.setCellValue(1);
        SXSSFCell cell3 = row.createCell(2);
        cell3.setCellFormula("=A1+B1");
        sxxsFWorkbook.write(fileOutputStream);
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
            Class ttt = c.getRawType();
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
            Class ttt = c.getRawType();
            System.out.println(ttt);
        } else {
            System.out.println("出错！！！");
        }

    }

}
