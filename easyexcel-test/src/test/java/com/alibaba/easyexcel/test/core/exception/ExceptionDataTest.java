package com.alibaba.easyexcel.test.core.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionDataTest {

    private static File file07;
    private static File file03;
    private static File fileException07;
    private static File fileException03;
    private static File fileCsv;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("exception.xlsx");
        file03 = TestFileUtil.createNewFile("exception.xls");
        fileCsv = TestFileUtil.createNewFile("exception.csv");
        fileException07 = TestFileUtil.createNewFile("exceptionThrow.xlsx");
        fileException03 = TestFileUtil.createNewFile("exceptionThrow.xls");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteCsv() throws Exception {
        readAndWrite(fileCsv);
    }

    @Test
    public void t11ReadAndWrite07() throws Exception {
        readAndWriteException(fileException07);
    }

    @Test
    public void t12ReadAndWrite03() throws Exception {
        readAndWriteException(fileException03);
    }

    private void readAndWriteException(File file) throws Exception {
        EasyExcel.write(new FileOutputStream(file), ExceptionData.class).sheet().doWrite(data());
        ArithmeticException exception = Assert.assertThrows(ArithmeticException.class, () -> EasyExcel.read(
            new FileInputStream(file), ExceptionData.class,
            new ExceptionThrowDataListener()).sheet().doRead());
        Assert.assertEquals("/ by zero",exception.getMessage());
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcel.write(new FileOutputStream(file), ExceptionData.class).sheet().doWrite(data());
        EasyExcel.read(new FileInputStream(file), ExceptionData.class, new ExceptionDataListener()).sheet().doRead();
    }

    private List<ExceptionData> data() {
        List<ExceptionData> list = new ArrayList<ExceptionData>();
        for (int i = 0; i < 10; i++) {
            ExceptionData simpleData = new ExceptionData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
