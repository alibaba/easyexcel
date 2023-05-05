package com.alibaba.easyexcel.test.core.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class WriteHandlerTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("writeHandler07.xlsx");
        file03 = TestFileUtil.createNewFile("writeHandler03.xls");
        fileCsv = TestFileUtil.createNewFile("writeHandlerCsv.csv");
    }

    @Test
    public void t01WorkbookWrite07() throws Exception {
        workbookWrite(file07);
    }

    @Test
    public void t02WorkbookWrite03() throws Exception {
        workbookWrite(file03);
    }

    @Test
    public void t03WorkbookWriteCsv() throws Exception {
        workbookWrite(fileCsv);
    }

    @Test
    public void t11SheetWrite07() throws Exception {
        sheetWrite(file07);
    }

    @Test
    public void t12SheetWrite03() throws Exception {
        sheetWrite(file03);
    }

    @Test
    public void t13SheetWriteCsv() throws Exception {
        sheetWrite(fileCsv);
    }

    @Test
    public void t21TableWrite07() throws Exception {
        tableWrite(file07);
    }

    @Test
    public void t22TableWrite03() throws Exception {
        tableWrite(file03);
    }

    @Test
    public void t23TableWriteCsv() throws Exception {
        tableWrite(fileCsv);
    }

    private void workbookWrite(File file) {
        WriteHandler writeHandler = new WriteHandler();
        EasyExcel.write(file).head(WriteHandlerData.class).registerWriteHandler(writeHandler).sheet().doWrite(data());
        writeHandler.afterAll();
    }

    private void sheetWrite(File file) {
        WriteHandler writeHandler = new WriteHandler();
        EasyExcel.write(file).head(WriteHandlerData.class).sheet().registerWriteHandler(writeHandler).doWrite(data());
        writeHandler.afterAll();
    }

    private void tableWrite(File file) {
        WriteHandler writeHandler = new WriteHandler();
        EasyExcel.write(file).head(WriteHandlerData.class).sheet().table(0).registerWriteHandler(writeHandler)
            .doWrite(data());
        writeHandler.afterAll();
    }

    private List<WriteHandlerData> data() {
        List<WriteHandlerData> list = new ArrayList<WriteHandlerData>();
        for (int i = 0; i < 1; i++) {
            WriteHandlerData data = new WriteHandlerData();
            data.setName("姓名" + i);
            list.add(data);
        }
        return list;
    }
}
