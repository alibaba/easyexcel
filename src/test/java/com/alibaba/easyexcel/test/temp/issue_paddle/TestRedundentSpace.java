package com.alibaba.easyexcel.test.temp.issue_paddle;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import org.junit.Test;

import java.io.File;

public class TestRedundentSpace {
    // initial the workbook, lisener and the sheet
    String fileName = TestFileUtil.getPath() + "issue2346" + File.separator + "test.xls";
    TestExcelDataLisener listener = new TestExcelDataLisener();
    ExcelReaderBuilder readWorkBook =  EasyExcel.read(fileName, TestExcel.class, listener);
    ExcelReaderSheetBuilder sheet = readWorkBook.sheet();

    // test whether the value 1 have space left
    @Test
    public void test1() {
        sheet.doRead();
        assert (listener.getAllData().get(0).getWeight().equals("9234g"));
    }

    // test whether the value 2 have space left
    @Test
    public void test2(){
        sheet.doRead();
        assert (listener.getAllData().get(1).getWeight().equals("876g"));
    }

}
