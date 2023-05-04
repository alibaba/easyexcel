package com.alibaba.easyexcel.test.core.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;

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
public class SimpleDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("simple07.xlsx");
        file03 = TestFileUtil.createNewFile("simple03.xls");
        fileCsv = TestFileUtil.createNewFile("simpleCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteCsv() {
        readAndWrite(fileCsv);
    }

    private void readAndWrite(File file) {
        EasyExcel.write(file, SimpleData.class).sheet().doWrite(data());
        EasyExcel.read(file, SimpleData.class, new SimpleDataListener()).sheet().doRead();
    }

    @Test
    public void t04ReadAndWrite07() throws Exception {
        readAndWriteInputStream(file07, ExcelTypeEnum.XLSX);
    }

    @Test
    public void t05ReadAndWrite03() throws Exception {
        readAndWriteInputStream(file03, ExcelTypeEnum.XLS);
    }

    @Test
    public void t06ReadAndWriteCsv() throws Exception {
        readAndWriteInputStream(fileCsv, ExcelTypeEnum.CSV);
    }

    private void readAndWriteInputStream(File file, ExcelTypeEnum excelTypeEnum) throws Exception {
        EasyExcel.write(new FileOutputStream(file), SimpleData.class).excelType(excelTypeEnum).sheet().doWrite(data());
        EasyExcel.read(new FileInputStream(file), SimpleData.class, new SimpleDataListener()).sheet().doRead();
    }

    @Test
    public void t11SynchronousRead07() {
        synchronousRead(file07);
    }

    @Test
    public void t12SynchronousRead03() {
        synchronousRead(file03);
    }

    @Test
    public void t13SynchronousReadCsv() {
        synchronousRead(fileCsv);
    }

    @Test
    public void t21SheetNameRead07() {
        List<Map<Integer, Object>> list = EasyExcel.read(
                TestFileUtil.readFile("simple" + File.separator + "simple07.xlsx"))
            .sheet("simple")
            .doReadSync();
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void t22PageReadListener07() {
        EasyExcel.read(file07, SimpleData.class,
                new PageReadListener<SimpleData>(dataList -> {
                    Assertions.assertEquals(5, dataList.size());
                }, 5))
            .sheet().doRead();
    }

    private void synchronousRead(File file) {
        // Synchronous read file
        List<Object> list = EasyExcel.read(file).head(SimpleData.class).sheet().doReadSync();
        Assertions.assertEquals(list.size(), 10);
        Assertions.assertTrue(list.get(0) instanceof SimpleData);
        Assertions.assertEquals(((SimpleData)list.get(0)).getName(), "姓名0");
    }

    private List<SimpleData> data() {
        List<SimpleData> list = new ArrayList<SimpleData>();
        for (int i = 0; i < 10; i++) {
            SimpleData simpleData = new SimpleData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
