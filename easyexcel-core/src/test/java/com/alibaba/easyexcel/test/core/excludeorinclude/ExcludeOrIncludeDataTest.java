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
    private static File excludeFieldName07;
    private static File excludeFieldName03;
    private static File excludeFieldNameCsv;
    private static File includeIndex07;
    private static File includeIndex03;
    private static File includeIndexCsv;
    private static File includeFieldName07;
    private static File includeFieldName03;
    private static File includeFieldNameCsv;

    @BeforeClass
    public static void init() {
        excludeIndex07 = TestFileUtil.createNewFile("excludeIndex.xlsx");
        excludeIndex03 = TestFileUtil.createNewFile("excludeIndex.xls");
        excludeIndexCsv = TestFileUtil.createNewFile("excludeIndex.csv");
        excludeFieldName07 = TestFileUtil.createNewFile("excludeFieldName.xlsx");
        excludeFieldName03 = TestFileUtil.createNewFile("excludeFieldName.xls");
        excludeFieldNameCsv = TestFileUtil.createNewFile("excludeFieldName.csv");
        includeIndex07 = TestFileUtil.createNewFile("includeIndex.xlsx");
        includeIndex03 = TestFileUtil.createNewFile("includeIndex.xls");
        includeIndexCsv = TestFileUtil.createNewFile("includeIndex.csv");
        includeFieldName07 = TestFileUtil.createNewFile("includeFieldName.xlsx");
        includeFieldName03 = TestFileUtil.createNewFile("includeFieldName.xls");
        includeFieldNameCsv = TestFileUtil.createNewFile("includeFieldName.csv");
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
    public void t11ExcludeFieldName07() {
        excludeFieldName(excludeFieldName07);
    }

    @Test
    public void t12ExcludeFieldName03() {
        excludeFieldName(excludeFieldName03);
    }

    @Test
    public void t13ExcludeFieldNameCsv() {
        excludeFieldName(excludeFieldNameCsv);
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
    public void t31IncludeFieldName07() {
        includeFieldName(includeFieldName07);
    }

    @Test
    public void t32IncludeFieldName03() {
        includeFieldName(includeFieldName03);
    }

    @Test
    public void t33IncludeFieldNameCsv() {
        includeFieldName(includeFieldNameCsv);
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

    private void excludeFieldName(File file) {
        Set<String> excludeColumnFieldNames = new HashSet<String>();
        excludeColumnFieldNames.add("column1");
        excludeColumnFieldNames.add("column3");
        excludeColumnFieldNames.add("column4");
        EasyExcel.write(file, ExcludeOrIncludeData.class).excludeColumnFieldNames(excludeColumnFieldNames).sheet()
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

    private void includeFieldName(File file) {
        Set<String> includeColumnFieldNames = new HashSet<String>();
        includeColumnFieldNames.add("column2");
        includeColumnFieldNames.add("column3");
        EasyExcel.write(file, ExcludeOrIncludeData.class).includeColumnFieldNames(includeColumnFieldNames).sheet()
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
