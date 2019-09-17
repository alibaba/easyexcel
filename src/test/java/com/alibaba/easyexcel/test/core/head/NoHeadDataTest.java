package com.alibaba.easyexcel.test.core.head;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

/**
 *
 * @author Jiaju Zhuang
 */
public class NoHeadDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("noHead07.xlsx");
        file03 = TestFileUtil.createNewFile("noHead03.xls");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) {
        EasyExcel.write(file, NoHeadData.class).needHead(Boolean.FALSE).sheet().doWrite(data());
        EasyExcel.read(file, NoHeadData.class, new NoHeadDataListener()).headRowNumber(0).sheet().doRead();
    }

    private List<NoHeadData> data() {
        List<NoHeadData> list = new ArrayList<NoHeadData>();
        NoHeadData data = new NoHeadData();
        data.setString("字符串0");
        list.add(data);
        return list;
    }
}
