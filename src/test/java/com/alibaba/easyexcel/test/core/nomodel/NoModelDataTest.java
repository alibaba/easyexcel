package com.alibaba.easyexcel.test.core.nomodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NoModelDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File fileRepeat07;
    private static File fileRepeat03;
    private static File fileRepeatCsv;


    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("noModel07.xlsx");
        file03 = TestFileUtil.createNewFile("noModel03.xls");
        fileCsv = TestFileUtil.createNewFile("noModelCsv.csv");
        fileRepeat07 = TestFileUtil.createNewFile("noModelRepeat07.xlsx");
        fileRepeat03 = TestFileUtil.createNewFile("noModelRepeat03.xls");
        fileRepeatCsv = TestFileUtil.createNewFile("noModelRepeatCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07, fileRepeat07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03, fileRepeat03);
    }

    @Test
    public void t03ReadAndWriteCsv() throws Exception {
        readAndWrite(fileCsv, fileRepeatCsv);
    }

    private void readAndWrite(File file, File fileRepeat) throws Exception {
        EasyExcel.write(file).sheet().doWrite(data());
        List<Map<Integer, String>> result = EasyExcel.read(file).headRowNumber(0).sheet().doReadSync();
        Assert.assertEquals(10, result.size());
        Map<Integer, String> data10 = result.get(9);
        Assert.assertEquals("string19", data10.get(0));
        Assert.assertEquals("109", data10.get(1));
        Assert.assertEquals("2020-01-01 01:01:01", data10.get(2));

        EasyExcel.write(fileRepeat).sheet().doWrite(result);
        result = EasyExcel.read(fileRepeat).headRowNumber(0).sheet().doReadSync();
        Assert.assertEquals(10, result.size());
        data10 = result.get(9);
        Assert.assertEquals("string19", data10.get(0));
        Assert.assertEquals("109", data10.get(1));
        Assert.assertEquals("2020-01-01 01:01:01", data10.get(2));
    }

    private List<List<Object>> data() throws Exception {
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<>();
            data.add("string1" + i);
            data.add(100 + i);
            data.add(DateUtils.parseDate("2020-01-01 01:01:01"));
            list.add(data);
        }
        return list;
    }
}
