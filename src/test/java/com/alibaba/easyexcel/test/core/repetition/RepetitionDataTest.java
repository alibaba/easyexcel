package com.alibaba.easyexcel.test.core.repetition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepetitionDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File fileTable07;
    private static File fileTable03;
    private static File fileTableCsv;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("repetition07.xlsx");
        file03 = TestFileUtil.createNewFile("repetition03.xls");
        fileCsv = TestFileUtil.createNewFile("repetitionCsv.csv");
        fileTable07 = TestFileUtil.createNewFile("repetitionTable07.xlsx");
        fileTable03 = TestFileUtil.createNewFile("repetitionTable03.xls");
        fileTableCsv = TestFileUtil.createNewFile("repetitionTableCsv.csv");
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
        ExcelWriter excelWriter = EasyExcel.write(file, RepetitionData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        excelWriter.write(data(), writeSheet).write(data(), writeSheet).finish();
        ExcelReader excelReader = EasyExcel.read(file, RepetitionData.class, new RepetitionDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet).finish();
    }

    @Test
    public void t11ReadAndWriteTable07() {
        readAndWriteTable(fileTable07);
    }

    @Test
    public void t12ReadAndWriteTable03() {
        readAndWriteTable(fileTable03);
    }

    @Test
    public void t13ReadAndWriteTableCsv() {
        readAndWriteTable(fileTableCsv);
    }

    private void readAndWriteTable(File file) {
        ExcelWriter excelWriter = EasyExcel.write(file, RepetitionData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        WriteTable writeTable = EasyExcel.writerTable(0).relativeHeadRowIndex(0).build();
        excelWriter.write(data(), writeSheet, writeTable).write(data(), writeSheet, writeTable).finish();
        ExcelReader excelReader = EasyExcel.read(file, RepetitionData.class, new RepetitionDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(2).build();
        excelReader.read(readSheet).finish();
    }

    private List<RepetitionData> data() {
        List<RepetitionData> list = new ArrayList<RepetitionData>();
        RepetitionData data = new RepetitionData();
        data.setString("字符串0");
        list.add(data);
        return list;
    }
}
