package com.alibaba.easyexcel.test.core.excludeorinclude;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
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
    private static File includeFieldNameOrder07;
    private static File includeFieldNameOrder03;
    private static File includeFieldNameOrderCsv;

    private static File includeFieldNameOrderIndex07;
    private static File includeFieldNameOrderIndex03;
    private static File includeFieldNameOrderIndexCsv;

    @BeforeAll
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
        includeFieldNameOrder07 = TestFileUtil.createNewFile("includeFieldNameOrder.xlsx");
        includeFieldNameOrder03 = TestFileUtil.createNewFile("includeFieldNameOrder.xls");
        includeFieldNameOrderCsv = TestFileUtil.createNewFile("includeFieldNameOrder.csv");
        includeFieldNameOrderIndex07 = TestFileUtil.createNewFile("includeFieldNameOrderIndex.xlsx");
        includeFieldNameOrderIndex03 = TestFileUtil.createNewFile("includeFieldNameOrderIndex.xls");
        includeFieldNameOrderIndexCsv = TestFileUtil.createNewFile("includeFieldNameOrderIndex.csv");
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

    @Test
    public void t41IncludeFieldNameOrder07() {
        includeFieldNameOrder(includeFieldNameOrder07);
    }

    @Test
    public void t42IncludeFieldNameOrder03() {
        includeFieldNameOrder(includeFieldNameOrder03);
    }

    @Test
    public void t43IncludeFieldNameOrderCsv() {
        includeFieldNameOrder(includeFieldNameOrderCsv);
    }

    @Test
    public void t41IncludeFieldNameOrderIndex07() {
        includeFieldNameOrderIndex(includeFieldNameOrderIndex07);
    }

    @Test
    public void t42IncludeFieldNameOrderIndex03() {
        includeFieldNameOrderIndex(includeFieldNameOrderIndex03);
    }

    @Test
    public void t43IncludeFieldNameOrderIndexCsv() {
        includeFieldNameOrderIndex(includeFieldNameOrderIndexCsv);
    }

    private void excludeIndex(File file) {
        Set<Integer> excludeColumnIndexes = new HashSet<Integer>();
        excludeColumnIndexes.add(0);
        excludeColumnIndexes.add(3);
        EasyExcel.write(file, ExcludeOrIncludeData.class).excludeColumnIndexes(excludeColumnIndexes).sheet()
                .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assertions.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assertions.assertEquals(2, record.size());
        Assertions.assertEquals("column2", record.get(0));
        Assertions.assertEquals("column3", record.get(1));

    }

    private void excludeFieldName(File file) {
        Set<String> excludeColumnFieldNames = new HashSet<String>();
        excludeColumnFieldNames.add("column1");
        excludeColumnFieldNames.add("column3");
        excludeColumnFieldNames.add("column4");
        EasyExcel.write(file, ExcludeOrIncludeData.class).excludeColumnFieldNames(excludeColumnFieldNames).sheet()
                .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assertions.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assertions.assertEquals(1, record.size());
        Assertions.assertEquals("column2", record.get(0));

    }

    private void includeIndex(File file) {
        Set<Integer> includeColumnIndexes = new HashSet<Integer>();
        includeColumnIndexes.add(1);
        includeColumnIndexes.add(2);
        EasyExcel.write(file, ExcludeOrIncludeData.class).includeColumnIndexes(includeColumnIndexes).sheet()
                .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assertions.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assertions.assertEquals(2, record.size());
        Assertions.assertEquals("column2", record.get(0));
        Assertions.assertEquals("column3", record.get(1));

    }

    private void includeFieldName(File file) {
        Set<String> includeColumnFieldNames = new HashSet<String>();
        includeColumnFieldNames.add("column2");
        includeColumnFieldNames.add("column3");
        EasyExcel.write(file, ExcludeOrIncludeData.class)
                .sheet()
                .includeColumnFieldNames(includeColumnFieldNames)
                .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assertions.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assertions.assertEquals(2, record.size());
        Assertions.assertEquals("column2", record.get(0));
        Assertions.assertEquals("column3", record.get(1));
    }

    private void includeFieldNameOrderIndex(File file) {
        List<Integer> includeColumnIndexes = new ArrayList<>();
        includeColumnIndexes.add(3);
        includeColumnIndexes.add(1);
        includeColumnIndexes.add(2);
        includeColumnIndexes.add(0);
        EasyExcel.write(file, ExcludeOrIncludeData.class)
                .includeColumnIndexes(includeColumnIndexes)
                .orderByIncludeColumn(true).
                sheet()
                .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assertions.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assertions.assertEquals(4, record.size());
        Assertions.assertEquals("column4", record.get(0));
        Assertions.assertEquals("column2", record.get(1));
        Assertions.assertEquals("column3", record.get(2));
        Assertions.assertEquals("column1", record.get(3));
    }

    private void includeFieldNameOrder(File file) {
        List<String> includeColumnFieldNames = new ArrayList<>();
        includeColumnFieldNames.add("column4");
        includeColumnFieldNames.add("column2");
        includeColumnFieldNames.add("column3");
        EasyExcel.write(file, ExcludeOrIncludeData.class)
                .includeColumnFieldNames(includeColumnFieldNames)
                .orderByIncludeColumn(true).
                sheet()
                .doWrite(data());
        List<Map<Integer, String>> dataMap = EasyExcel.read(file).sheet().doReadSync();
        Assertions.assertEquals(1, dataMap.size());
        Map<Integer, String> record = dataMap.get(0);
        Assertions.assertEquals(3, record.size());
        Assertions.assertEquals("column4", record.get(0));
        Assertions.assertEquals("column2", record.get(1));
        Assertions.assertEquals("column3", record.get(2));
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
