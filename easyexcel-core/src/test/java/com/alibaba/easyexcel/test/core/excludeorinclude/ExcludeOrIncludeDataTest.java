package com.alibaba.easyexcel.test.core.excludeorinclude;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class ExcludeOrIncludeDataTest {

    private static File excludeIndex07;
    private static File excludeIndex03;
    private static File excludeIndexCsv;
    private static File excludeFiledName07;
    private static File excludeFiledName03;
    private static File excludeFiledNameCsv;
    private static File includeIndex07;
    private static File includeIndex03;
    private static File includeIndexCsv;
    private static File includeFiledName07;
    private static File includeFiledName03;
    private static File includeFiledNameCsv;

    @BeforeClass
    public static void init() {
        excludeIndex07 = TestFileUtil.createNewFile("excludeIndex.xlsx");
        excludeIndex03 = TestFileUtil.createNewFile("excludeIndex.xls");
        excludeIndexCsv = TestFileUtil.createNewFile("excludeIndex.csv");
        excludeFiledName07 = TestFileUtil.createNewFile("excludeFiledName.xlsx");
        excludeFiledName03 = TestFileUtil.createNewFile("excludeFiledName.xls");
        excludeFiledNameCsv = TestFileUtil.createNewFile("excludeFiledName.csv");
        includeIndex07 = TestFileUtil.createNewFile("includeIndex.xlsx");
        includeIndex03 = TestFileUtil.createNewFile("includeIndex.xls");
        includeIndexCsv = TestFileUtil.createNewFile("includeIndex.csv");
        includeFiledName07 = TestFileUtil.createNewFile("includeFiledName.xlsx");
        includeFiledName03 = TestFileUtil.createNewFile("includeFiledName.xls");
        includeFiledNameCsv = TestFileUtil.createNewFile("includeFiledName.csv");
    }

    @Test
    public void t01ExcludeIndex07() {
        excludeIndex(excludeIndex07);
    }

    @Test
    public void t02ExcludeIndex03() {
        excludeIndex(excludeIndex03);
    }

    @Test
    public void t03ExcludeIndexCsv() {
        excludeIndex(excludeIndexCsv);
    }

    @Test
    public void t11ExcludeFiledName07() {
        excludeFiledName(excludeFiledName07);
    }

    @Test
    public void t12ExcludeFiledName03() {
        excludeFiledName(excludeFiledName03);
    }

    @Test
    public void t13ExcludeFiledNameCsv() {
        excludeFiledName(excludeFiledNameCsv);
    }


    @Test
    public void t21IncludeIndex07() {
        includeIndex(includeIndex07);
    }

    @Test
    public void t22IncludeIndex03() {
        includeIndex(includeIndex03);
    }

    @Test
    public void t23IncludeIndexCsv() {
        includeIndex(includeIndexCsv);
    }

    @Test
    public void t31IncludeFiledName07() {
        includeFiledName(includeFiledName07);
    }

    @Test
    public void t32IncludeFiledName03() {
        includeFiledName(includeFiledName03);
    }

    @Test
    public void t33IncludeFiledNameCsv() {
        includeFiledName(includeFiledNameCsv);
    }

    private void excludeIndex(File file) {
        Set<Integer> excludeColumnIndexes = new HashSet<Integer>();
        excludeColumnIndexes.add(0);
        excludeColumnIndexes.add(3);
        EasyExcel.write(file, ExcludeOrIncludeData.class).excludeColumnIndexes(excludeColumnIndexes).sheet()
            .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assert.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assert.assertEquals(2, record.size());
        Assert.assertEquals("column2", record.get(0));
        Assert.assertEquals("column3", record.get(1));

    }

    private void excludeFiledName(File file) {
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("column1");
        excludeColumnFiledNames.add("column3");
        excludeColumnFiledNames.add("column4");
        EasyExcel.write(file, ExcludeOrIncludeData.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet()
            .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assert.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assert.assertEquals(1, record.size());
        Assert.assertEquals("column2", record.get(0));

    }

    private void includeIndex(File file) {
        Set<Integer> includeColumnIndexes = new HashSet<Integer>();
        includeColumnIndexes.add(1);
        includeColumnIndexes.add(2);
        EasyExcel.write(file, ExcludeOrIncludeData.class).includeColumnIndexes(includeColumnIndexes).sheet()
            .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assert.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assert.assertEquals(2, record.size());
        Assert.assertEquals("column2", record.get(0));
        Assert.assertEquals("column3", record.get(1));

    }

    private void includeFiledName(File file) {
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("column2");
        includeColumnFiledNames.add("column3");
        EasyExcel.write(file, ExcludeOrIncludeData.class).includeColumnFiledNames(includeColumnFiledNames).sheet()
            .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assert.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assert.assertEquals(2, record.size());
        Assert.assertEquals("column2", record.get(0));
        Assert.assertEquals("column3", record.get(1));
    }

    private List<ExcludeOrIncludeData> data() {
        List<ExcludeOrIncludeData> list = new ArrayList<ExcludeOrIncludeData>();
        ExcludeOrIncludeData excludeOrIncludeData = new ExcludeOrIncludeData();
        excludeOrIncludeData.setColumn1("column1");
        excludeOrIncludeData.setColumn2("column2");
        excludeOrIncludeData.setColumn3("column3");
        excludeOrIncludeData.setColumn4("column4");
        list.add(excludeOrIncludeData);
        return list;
    }
}
