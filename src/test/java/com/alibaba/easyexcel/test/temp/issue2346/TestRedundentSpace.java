package com.alibaba.easyexcel.test.temp.issue2346;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import org.apache.tomcat.websocket.server.WsHttpUpgradeHandler;
import org.junit.Test;

import java.io.File;

public class TestRedundentSpace {
    // initial the workbook, lisener and the sheet
    String fileName = TestFileUtil.getPath() + "issue2346" + File.separator + "111.xls";
    TestExcelDataLisener listener = new TestExcelDataLisener();
    ExcelReaderBuilder readWorkBook =  EasyExcel.read(fileName, TestExcel.class, listener);
    ExcelReaderSheetBuilder sheet = readWorkBook.sheet();

    // test whether the value 1 have space left
    @Test
    public void test1() {
        sheet.doRead();
        assert (listener.getAllData().get(0).getDebitIncurred().equals("-200.00"));
    }

    // test whether the value 2 have space left
    @Test
    public void test2(){
        sheet.doRead();
        assert (listener.getAllData().get(1).getDebitIncurred().equals("-200.00"));
    }

}
