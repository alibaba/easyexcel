package com.alibaba.easyexcel.test.core.noncamel;

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
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnCamelDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;


    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("unCame07.xlsx");
        file03 = TestFileUtil.createNewFile("unCame03.xls");
        fileCsv = TestFileUtil.createNewFile("unCameCsv.csv");
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
        EasyExcel.write(file, UnCamelData.class).sheet().doWrite(data());
        EasyExcel.read(file, UnCamelData.class, new UnCamelDataListener()).sheet().doRead();
    }

    private List<UnCamelData> data() {
        List<UnCamelData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UnCamelData unCamelData = new UnCamelData();
            unCamelData.setString1("string1");
            unCamelData.setString2("string2");
            unCamelData.setSTring3("string3");
            unCamelData.setSTring4("string4");
            unCamelData.setSTRING5("string5");
            unCamelData.setSTRing6("string6");
            list.add(unCamelData);
        }
        return list;
    }
}
