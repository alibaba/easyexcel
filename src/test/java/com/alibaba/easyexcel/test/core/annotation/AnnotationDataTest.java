package com.alibaba.easyexcel.test.core.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jiaju Zhuang
 */
public class AnnotationDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("annotation07.xlsx");
        file03 = TestFileUtil.createNewFile("annotation03.xls");
        fileCsv = TestFileUtil.createNewFile("annotationCsv.csv");
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

    private void readAndWrite(File file) throws Exception {
        EasyExcel.write().file(file).head(AnnotationData.class).sheet().doWrite(data());
        EasyExcel.read().file(file).head(AnnotationData.class).registerReadListener(new AnnotationDataListener())
            .sheet().doRead();

        if (file == fileCsv) {
            return;
        }

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Assert.assertEquals(50 * 256, sheet.getColumnWidth(0), 0);

        Row row0 = sheet.getRow(0);
        Assert.assertEquals(1000, row0.getHeight(), 0);

        Row row1 = sheet.getRow(1);
        Assert.assertEquals(2000, row1.getHeight(), 0);
    }

    private List<AnnotationData> data() throws Exception {
        List<AnnotationData> list = new ArrayList<>();
        AnnotationData data = new AnnotationData();
        data.setDate(DateUtils.parseDate("2020-01-01 01:01:01"));
        data.setNumber(99.99);
        data.setIgnore("忽略");
        data.setTransientString("忽略");
        list.add(data);
        return list;
    }
}
