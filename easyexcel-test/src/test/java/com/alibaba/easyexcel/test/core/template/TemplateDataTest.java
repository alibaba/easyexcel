package com.alibaba.easyexcel.test.core.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateDataTest {

    private static File file07;
    private static File file03;


    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("template07.xlsx");
        file03 = TestFileUtil.createNewFile("template03.xls");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite07(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite03(file03);
    }

    private void readAndWrite07(File file) {
        EasyExcel.write(file, TemplateData.class)
            .withTemplate(TestFileUtil.readFile("template" + File.separator + "template07.xlsx")).sheet()
            .doWrite(data());
        EasyExcel.read(file, TemplateData.class, new TemplateDataListener()).headRowNumber(3).sheet().doRead();
    }

    private void readAndWrite03(File file) {
        EasyExcel.write(file, TemplateData.class)
            .withTemplate(TestFileUtil.readFile("template" + File.separator + "template03.xls")).sheet()
            .doWrite(data());
        EasyExcel.read(file, TemplateData.class, new TemplateDataListener()).headRowNumber(3).sheet().doRead();
    }

    private List<TemplateData> data() {
        List<TemplateData> list = new ArrayList<TemplateData>();
        TemplateData data = new TemplateData();
        data.setString0("字符串0");
        data.setString1("字符串01");
        TemplateData data1 = new TemplateData();
        data1.setString0("字符串1");
        data1.setString1("字符串11");
        list.add(data);
        list.add(data1);
        return list;
    }
}
