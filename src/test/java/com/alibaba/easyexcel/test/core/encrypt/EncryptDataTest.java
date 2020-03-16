package com.alibaba.easyexcel.test.core.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EncryptDataTest {

    private static File file07;
    private static File file03;
    private static File file07OutputStream;
    private static File file03OutputStream;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("encrypt07.xlsx");
        file03 = TestFileUtil.createNewFile("encrypt03.xls");
        file07OutputStream = TestFileUtil.createNewFile("encryptOutputStream07.xlsx");
        file03OutputStream = TestFileUtil.createNewFile("encryptOutputStream03.xls");
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
    public void t03ReadAndWriteStream07() throws Exception {
        readAndWriteStream(file07OutputStream, ExcelTypeEnum.XLSX);
    }

    @Test
    public void t04ReadAndWriteStream03() throws Exception {
        readAndWriteStream(file03OutputStream, ExcelTypeEnum.XLS);
    }

    private void readAndWrite(File file) {
        EasyExcel.write(file, EncryptData.class).password("123456").sheet().doWrite(data());
        EasyExcel.read(file, EncryptData.class, new EncryptDataListener()).password("123456").sheet().doRead();
    }

    private void readAndWriteStream(File file, ExcelTypeEnum excelType) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        EasyExcel.write(fileOutputStream, EncryptData.class).password("123456").excelType(excelType).sheet()
            .doWrite(data());
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(file);
        EasyExcel.read(fileInputStream, EncryptData.class, new EncryptDataListener()).password("123456").sheet()
            .doRead();
    }

    private List<SimpleData> data() {
        List<SimpleData> list = new ArrayList<SimpleData>();
        for (int i = 0; i < 10; i++) {
            SimpleData simpleData = new SimpleData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
