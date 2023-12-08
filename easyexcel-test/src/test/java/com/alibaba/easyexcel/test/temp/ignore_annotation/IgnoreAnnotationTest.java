package com.alibaba.easyexcel.test.temp.ignore_annotation;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;

/**
 * @author atdt
 */
public class IgnoreAnnotationTest {

    /**
     * 父类与子类存在相同名字属性，
     * 并且子类注解为 ExcelIgnore，父类注解为 ExcelProperty 时
     * 输出文档中忽略该属性
     */
    @Test
    public void test1() throws Exception {
        String fileName = TestFileUtil.getPath() + "excelIgnoreAnnotation" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Child1.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("test").build();
            Child1 child = new Child1();
            child.setAddress("武汉");
            child.setName("张三");
            child.setPhone("123");
            excelWriter.write(Collections.singletonList(child), writeSheet);
        }

        Sheet sheet;
        try (Workbook workbook = WorkbookFactory.create(new File(fileName))) {
            sheet = workbook.getSheet("test");
        }

        /*
          预想结果（phone属性被忽略）
          | 地址（子类定义） | 名字（父类定义） |
          | --- | --- |
          | 武汉 | 张三 |
         */
        Assertions.assertEquals(sheet.getLastRowNum(), 1);
        Assertions.assertEquals("地址", sheet.getRow(0).getCell(0).getStringCellValue(), "表头不正");
        Assertions.assertEquals("名字", sheet.getRow(0).getCell(1).getStringCellValue(), "表头不正");
        Assertions.assertNull(sheet.getRow(0).getCell(2));

        Assertions.assertEquals("武汉", sheet.getRow(1).getCell(0).getStringCellValue(), "地址不正");
        Assertions.assertEquals("张三", sheet.getRow(1).getCell(1).getStringCellValue(), "名字不正");
        Assertions.assertNull(sheet.getRow(1).getCell(2));
    }

    /**
     * 父类与子类存在相同名字属性，
     * 并且子类注解为 ExcelIgnore，父类注解为 ExcelIgnore 时
     * 输出文档中忽略该属性
     */
    @Test
    public void test2() throws Exception {
        String fileName = TestFileUtil.getPath() + "excelIgnoreAnnotation" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Child2.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("test").build();
            Child2 child = new Child2();
            child.setAddress("武汉");
            child.setName("张三");
            child.setPhone("123");
            excelWriter.write(Collections.singletonList(child), writeSheet);
        }

        Sheet sheet;
        try (Workbook workbook = WorkbookFactory.create(new File(fileName))) {
            sheet = workbook.getSheet("test");
        }

        /*
          预想结果（phone属性被忽略）
          | 地址（子类定义） | 名字（父类定义） |
          | --- | --- |
          | 武汉 | 张三 |
         */
        Assertions.assertEquals(sheet.getLastRowNum(), 1);
        Assertions.assertEquals("地址", sheet.getRow(0).getCell(0).getStringCellValue(), "表头不正");
        Assertions.assertEquals("名字", sheet.getRow(0).getCell(1).getStringCellValue(), "表头不正");
        Assertions.assertNull(sheet.getRow(0).getCell(2));

        Assertions.assertEquals("武汉", sheet.getRow(1).getCell(0).getStringCellValue(), "地址不正");
        Assertions.assertEquals("张三", sheet.getRow(1).getCell(1).getStringCellValue(), "名字不正");
        Assertions.assertNull(sheet.getRow(1).getCell(2));
    }

    /**
     * 父类与子类存在相同名字属性，
     * 并且子类注解为 ExcelProperty，父类注解也为 ExcelProperty 时
     * 输出文档中只包含子类属性，不包含父类属性
     */
    @Test
    public void test3() throws Exception {
        String fileName = TestFileUtil.getPath() + "excelIgnoreAnnotation" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Child3.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("test").build();
            Child3 child = new Child3();
            child.setAddress("武汉");
            child.setName("张三");
            child.setPhone("123");
            excelWriter.write(Collections.singletonList(child), writeSheet);
        }

        Sheet sheet;
        try (Workbook workbook = WorkbookFactory.create(new File(fileName))) {
            sheet = workbook.getSheet("test");
        }

        /*
          预想结果（父类phone属性被忽略）
          | 地址（子类定义） | 名字（父类定义） | 电话（子类定义） |
          | --- | --- | --- |
          | 武汉 | 张三 | 123 |
         */
        Assertions.assertEquals(sheet.getLastRowNum(), 1);
        Assertions.assertEquals("地址", sheet.getRow(0).getCell(0).getStringCellValue(), "表头不正");
        Assertions.assertEquals("电话", sheet.getRow(0).getCell(1).getStringCellValue(), "表头不正");
        Assertions.assertEquals("名字", sheet.getRow(0).getCell(2).getStringCellValue(), "表头不正");
        Assertions.assertNull(sheet.getRow(0).getCell(3));

        Assertions.assertEquals("武汉", sheet.getRow(1).getCell(0).getStringCellValue(), "地址不正");
        Assertions.assertEquals("123", sheet.getRow(1).getCell(1).getStringCellValue(), "电话不正");
        Assertions.assertEquals("张三", sheet.getRow(1).getCell(2).getStringCellValue(), "名字不正");
        Assertions.assertNull(sheet.getRow(1).getCell(3));
    }

    /**
     * 父类与子类存在相同名字属性，
     * 并且子类注解为 ExcelProperty，父类注解为 ExcelIgnore 时
     * 输出文档中包含该属性
     */
    @Test
    public void test4() throws Exception {
        String fileName = TestFileUtil.getPath() + "excelIgnoreAnnotation" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Child4.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("test").build();
            Child4 child = new Child4();
            child.setAddress("武汉");
            child.setName("张三");
            child.setPhone("123");
            excelWriter.write(Collections.singletonList(child), writeSheet);
        }

        Sheet sheet;
        try (Workbook workbook = WorkbookFactory.create(new File(fileName))) {
            sheet = workbook.getSheet("test");
        }

        /*
          预想结果
          | 地址（子类定义） | 电话（父类定义） | 名字（子类定义） |
          | --- | --- | --- |
          | 武汉 | 123 | 张三 |
         */
        Assertions.assertEquals(sheet.getLastRowNum(), 1);
        Assertions.assertEquals("地址", sheet.getRow(0).getCell(0).getStringCellValue(), "表头不正");
        Assertions.assertEquals("电话", sheet.getRow(0).getCell(1).getStringCellValue(), "表头不正");
        Assertions.assertEquals("名字", sheet.getRow(0).getCell(2).getStringCellValue(), "表头不正");
        Assertions.assertNull(sheet.getRow(0).getCell(3));

        Assertions.assertEquals("武汉", sheet.getRow(1).getCell(0).getStringCellValue(), "地址不正");
        Assertions.assertEquals("123", sheet.getRow(1).getCell(1).getStringCellValue(), "名字不正");
        Assertions.assertEquals("张三", sheet.getRow(1).getCell(2).getStringCellValue(), "电话不正");
        Assertions.assertNull(sheet.getRow(1).getCell(3));
    }

}
