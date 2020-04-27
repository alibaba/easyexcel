package com.alibaba.easyexcel.test.core.handler;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.core.head.ListHeadDataListener;
import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WriteHandlerTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("writeHandler07.xlsx");
        file03 = TestFileUtil.createNewFile("writeHandler03.xls");
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
    public void t03SheetWrite07() throws Exception {
        sheetWrite(file07);
    }

    @Test
    public void t04SheetWrite03() throws Exception {
        sheetWrite(file03);
    }

    @Test
    public void t05TableWrite07() throws Exception {
        workbookWrite(file07);
        tableWrite(file07);
    }

    @Test
    public void t06TableWrite03() throws Exception {
        tableWrite(file03);
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
