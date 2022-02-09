package com.alibaba.easyexcel.test.core.simple;

import java.io.File;
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
public class SimpleDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;

    @BeforeClass
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
    public void t05SheetNameRead07() {
        EasyExcel.read(TestFileUtil.readFile("simple" + File.separator + "simple07.xlsx"), SimpleData.class,
            new SimpleDataSheetNameListener()).sheet("simple").doRead();
    }

    private void synchronousRead(File file) {
        // Synchronous read file
        List<Object> list = EasyExcel.read(file).head(SimpleData.class).sheet().doReadSync();
        Assert.assertEquals(list.size(), 10);
        Assert.assertTrue(list.get(0) instanceof SimpleData);
        Assert.assertEquals(((SimpleData)list.get(0)).getName(), "姓名0");
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
