package com.alibaba.easyexcel.test.core.head;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListHeadDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;


    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("listHead07.xlsx");
        file03 = TestFileUtil.createNewFile("listHead03.xls");
        fileCsv = TestFileUtil.createNewFile("listHeadCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteCsv() throws Exception {
        readAndWrite(fileCsv);
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcel.write(file).head(head()).sheet().doWrite(data());
        EasyExcel.read(file).registerReadListener(new ListHeadDataListener()).sheet().doRead();
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串");
        List<String> head1 = new ArrayList<String>();
        head1.add("数字");
        List<String> head2 = new ArrayList<String>();
        head2.add("日期");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> data() throws ParseException {
        List<List<Object>> list = new ArrayList<List<Object>>();
        List<Object> data0 = new ArrayList<Object>();
        data0.add("字符串0");
        data0.add(1);
        data0.add(DateUtils.parseDate("2020-01-01 01:01:01"));
        data0.add("额外数据");
        list.add(data0);
        return list;
    }
}
