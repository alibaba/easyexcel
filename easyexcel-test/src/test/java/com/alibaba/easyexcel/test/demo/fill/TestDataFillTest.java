package com.alibaba.easyexcel.test.demo.fill;


import java.io.File;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.junit.Test;
public class TestDataFillTest {

    @Test
    public void simpleFill1() {

        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
    //        String templateFileName = TestData1.class.getResource("/").getPath();
        String templateFileName = TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "issue1926.xlsx";
        // 方案1 根据对象填充
        String fileName = TestFileUtil.getPath() + "simpleFill" + "issue-1926_repair1 .xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        TestData1 fillData = new TestData1();

        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);


    }

    @Test
    public void simpleFill2() {

        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "issue1926.xlsx";
        // 方案1 根据对象填充
        String fileName = TestFileUtil.getPath() + "simpleFill" + "issue-1926_repair2 .xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        TestData2 fillData = new TestData2();

        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);


    }
}
