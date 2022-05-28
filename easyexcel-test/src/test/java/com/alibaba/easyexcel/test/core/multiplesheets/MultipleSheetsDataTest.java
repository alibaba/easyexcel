package com.alibaba.easyexcel.test.core.multiplesheets;

import java.io.File;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultipleSheetsDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.readFile("multiplesheets" + File.separator + "multiplesheets.xlsx");
        file03 = TestFileUtil.readFile("multiplesheets" + File.separator + "multiplesheets.xls");
    }

    @Test
    public void t01Read07() {
        read(file07);
    }

    @Test
    public void t02Read03() {
        read(file03);
    }

    @Test
    public void t03Read07All() {
        readAll(file07);
    }

    @Test
    public void t04Read03All() {
        readAll(file03);
    }

    private void read(File file) {
        MultipleSheetsListener multipleSheetsListener = new MultipleSheetsListener();
        try (ExcelReader excelReader = EasyExcel.read(file, MultipleSheetsData.class, multipleSheetsListener).build()) {
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            int count = 1;
            for (ReadSheet readSheet : sheets) {
                excelReader.read(readSheet);
                Assert.assertEquals(multipleSheetsListener.getList().size(), count);
                count++;
            }
        }
    }

    private void readAll(File file) {
        EasyExcel.read(file, MultipleSheetsData.class, new MultipleSheetsListener()).doReadAll();
    }

}
