package com.alibaba.easyexcel.test.core.fill.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FillAnnotationDataTest {

    private static File file07;
    private static File file03;
    private static File fileTemplate07;
    private static File fileTemplate03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("fillAnnotation07.xlsx");
        file03 = TestFileUtil.createNewFile("fillAnnotation03.xls");
        fileTemplate07 = TestFileUtil.readFile("fill" + File.separator + "annotation.xlsx");
        fileTemplate03 = TestFileUtil.readFile("fill" + File.separator + "annotation.xls");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07, fileTemplate07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03, fileTemplate03);
    }

    private void readAndWrite(File file, File fileTemplate) throws Exception {
        EasyExcel.write().file(file).head(FillAnnotationData.class).withTemplate(fileTemplate).sheet().doFill(data());

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        Row row1 = sheet.getRow(1);
        Assert.assertEquals(2000, row1.getHeight(), 0);
        Cell cell10 = row1.getCell(0);
        Date date = cell10.getDateCellValue();
        Assert.assertEquals(DateUtils.parseDate("2020-01-01 01:01:01").getTime(), date.getTime());
        String dataFormatString = cell10.getCellStyle().getDataFormatString();
        Assert.assertEquals("yyyy年MM月dd日HH时mm分ss秒", dataFormatString);
        Cell cell11 = row1.getCell(1);
        Assert.assertEquals(99.99, cell11.getNumericCellValue(), 2);
        boolean hasMerge = false;
        for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
            if (mergedRegion.getFirstRow() == 1 && mergedRegion.getLastRow() == 1
                && mergedRegion.getFirstColumn() == 2 && mergedRegion.getLastColumn() == 3) {
                hasMerge = true;
            }
        }
        Assert.assertTrue(hasMerge);
    }

    private List<FillAnnotationData> data() throws Exception {
        List<FillAnnotationData> list = new ArrayList<>();
        FillAnnotationData data = new FillAnnotationData();
        data.setDate(DateUtils.parseDate("2020-01-01 01:01:01"));
        data.setNumber(99.99);
        data.setString1("string1");
        data.setString2("string2");
        list.add(data);
        list.add(data);
        list.add(data);
        list.add(data);
        list.add(data);
        return list;
    }
}
