package com.alibaba.easyexcel.test.core.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("simple07.xlsx");
        file03 = TestFileUtil.createNewFile("simple03.xls");
    }

    @Test
    public void T01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void T02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcelFactory.write(new FileOutputStream(file), ExceptionData.class).sheet().doWrite(data()).finish();
        EasyExcelFactory.read(new FileInputStream(file), ExceptionData.class, new ExceptionDataListener()).sheet()
            .doRead().finish();
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
}
