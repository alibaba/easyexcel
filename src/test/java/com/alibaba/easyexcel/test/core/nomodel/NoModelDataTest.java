package com.alibaba.easyexcel.test.core.nomodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NoModelDataTest {

    private static File file07;
    private static File file03;
    private static File fileRepeat07;
    private static File fileRepeat03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("noModel07.xlsx");
        file03 = TestFileUtil.createNewFile("noModel03.xls");
        fileRepeat07 = TestFileUtil.createNewFile("noModelRepeat07.xlsx");
        fileRepeat03 = TestFileUtil.createNewFile("noModelRepeat03.xls");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07, fileRepeat07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03, fileRepeat03);
    }

    private void readAndWrite(File file, File fileRepeat) {
        EasyExcel.write(file).sheet().doWrite(data());
        List<Map<Integer, String>> result = EasyExcel.read(file).headRowNumber(0).sheet().doReadSync();
        Assert.assertEquals(10, result.size());
        Map<Integer, String> data10 = result.get(9);
        Assert.assertEquals("string19", data10.get(0));
        EasyExcel.write(fileRepeat).sheet().doWrite(result);
        result = EasyExcel.read(fileRepeat).headRowNumber(0).sheet().doReadSync();
        Assert.assertEquals(10, result.size());
        data10 = result.get(9);
        Assert.assertEquals("string19", data10.get(0));
    }

    private List<List<String>> data() {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> data = new ArrayList<>();
            data.add("string1" + i);
            data.add("string2" + i);
            data.add("string3" + i);
            list.add(data);
        }
        return list;
    }
}
