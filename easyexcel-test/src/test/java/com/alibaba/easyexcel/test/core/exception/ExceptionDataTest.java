package com.alibaba.easyexcel.test.core.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ExceptionDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File fileExcelAnalysisStopSheetException07;
    private static File fileExcelAnalysisStopSheetException03;
    private static File fileExcelAnalysisStopSheetExceptionCsv;
    private static File fileException07;
    private static File fileException03;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("exception.xlsx");
        file03 = TestFileUtil.createNewFile("exception.xls");
        fileCsv = TestFileUtil.createNewFile("exception.csv");
        fileExcelAnalysisStopSheetException07 = TestFileUtil.createNewFile("excelAnalysisStopSheetException.xlsx");
        fileExcelAnalysisStopSheetException03 = TestFileUtil.createNewFile("excelAnalysisStopSheetException.xls");
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

    @Test
    public void t21ReadAndWrite07() throws Exception {
        readAndWriteExcelAnalysisStopSheetException(fileExcelAnalysisStopSheetException07);
    }

    @Test
    public void t22ReadAndWrite03() throws Exception {
        readAndWriteExcelAnalysisStopSheetException(fileExcelAnalysisStopSheetException03);
    }


    private void readAndWriteExcelAnalysisStopSheetException(File file) throws Exception {
        try (ExcelWriter excelWriter = EasyExcel.write(file, ExceptionData.class).build()) {
            for (int i = 0; i < 5; i++) {
                String sheetName = "sheet" + i;
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName).build();
                List<ExceptionData> data = data(sheetName);
                excelWriter.write(data, writeSheet);
            }
        }

        ExcelAnalysisStopSheetExceptionDataListener excelAnalysisStopSheetExceptionDataListener
            = new ExcelAnalysisStopSheetExceptionDataListener();
        EasyExcel.read(file, ExceptionData.class, excelAnalysisStopSheetExceptionDataListener).doReadAll();
        Map<Integer, List<String>> dataMap = excelAnalysisStopSheetExceptionDataListener.getDataMap();
        Assertions.assertEquals(5, dataMap.size());
        for (int i = 0; i < 5; i++) {
            List<String> sheetDataList = dataMap.get(i);
            Assertions.assertNotNull(sheetDataList);
            Assertions.assertEquals(5, sheetDataList.size());
            String sheetName = "sheet" + i;

            for (String sheetData : sheetDataList) {
                Assertions.assertTrue(sheetData.startsWith(sheetName));
            }
        }
    }

    private void readAndWriteException(File file) throws Exception {
        EasyExcel.write(new FileOutputStream(file), ExceptionData.class).sheet().doWrite(data());
        ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class, () -> EasyExcel.read(
            new FileInputStream(file), ExceptionData.class,
            new ExceptionThrowDataListener()).sheet().doRead());
        Assertions.assertEquals("/ by zero", exception.getMessage());
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

    private List<ExceptionData> data(String prefix) {
        List<ExceptionData> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ExceptionData simpleData = new ExceptionData();
            simpleData.setName(prefix + "-姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
