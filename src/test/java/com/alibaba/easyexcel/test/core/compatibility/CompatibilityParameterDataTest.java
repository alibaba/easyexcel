package com.alibaba.easyexcel.test.core.compatibility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;

/**
 *
 * @author Jiaju Zhuang
 */
public class CompatibilityParameterDataTest {

    private static File file;

    @BeforeClass
    public static void init() {
        file = TestFileUtil.createNewFile("compatibilityParameter.xlsx");
    }

    @Test
    public void t01ReadAndWrite() throws Exception {
        readAndWrite1(file);
        readAndWrite2(file);
        readAndWrite3(file);
        readAndWrite4(file);
        readAndWrite5(file);
        readAndWrite6(file);
    }

    private void readAndWrite1(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = EasyExcel.getWriter(out);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(data(), sheet1);
        writer.finish();
        out.close();

        InputStream inputStream = new FileInputStream(file);
        EasyExcel.readBySax(inputStream, new Sheet(1, 0), new CompatibilityDataListener());
        inputStream.close();
    }

    private void readAndWrite2(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = EasyExcel.getWriter(out, null, false);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(data(), sheet1);
        writer.finish();
        out.close();

        InputStream inputStream = new FileInputStream(file);
        EasyExcel.readBySax(inputStream, new Sheet(1, 0), new CompatibilityDataListener());
        inputStream.close();
    }

    private void readAndWrite3(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = new ExcelWriter(out, null);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(data(), sheet1);
        writer.finish();
        out.close();

        InputStream inputStream = new FileInputStream(file);
        ExcelReader excelReader = new ExcelReader(inputStream, null, null, new CompatibilityDataListener());
        excelReader.read(new Sheet(1, 0));
        inputStream.close();

    }

    private void readAndWrite4(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = new ExcelWriter(null, out, null, null);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(data(), sheet1, null);
        writer.finish();
        out.close();

        InputStream inputStream = new FileInputStream(file);
        ExcelReader excelReader = new ExcelReader(inputStream, null, new CompatibilityDataListener());
        excelReader.read(new Sheet(1, 0));
        inputStream.close();
    }

    private void readAndWrite5(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = EasyExcel.getWriterWithTemp(null, out, null, false);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(data(), sheet1, null);
        writer.finish();
        out.close();

        InputStream inputStream = new FileInputStream(file);
        ExcelReader excelReader = EasyExcel.getReader(inputStream, new CompatibilityDataListener());
        excelReader.read(new Sheet(1, 0));
        inputStream.close();
    }

    private void readAndWrite6(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = EasyExcel.getWriterWithTempAndHandler(null, out, null, false, null);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");
        writer.write0(data(), sheet1, null);
        writer.finish();
        out.close();

        InputStream inputStream = new FileInputStream(file);
        ExcelReader excelReader = EasyExcel.getReader(inputStream, new CompatibilityDataListener());
        excelReader.read(new Sheet(1, 0));
        inputStream.close();
    }

    private List<CompatibilityData> data() {
        List<CompatibilityData> list = new ArrayList<CompatibilityData>();
        for (int i = 0; i < 10; i++) {
            CompatibilityData data = new CompatibilityData();
            data.setString0("字符串0" + i);
            data.setString1("字符串1" + i);
            list.add(data);
        }
        return list;
    }

}
