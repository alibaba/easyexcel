package com.alibaba.easyexcel.test.core.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;

/**
 *
 * @author Jiaju Zhuang
 */
public class AnnotationDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("annotation07.xlsx");
        file03 = TestFileUtil.createNewFile("annotation03.xls");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcel.write().file(file).head(AnnotationData.class).sheet().doWrite(data());
        EasyExcel.read().file(file).head(AnnotationData.class).registerReadListener(new AnnotationDataListener())
            .sheet().doRead();
    }

    private List<AnnotationData> data() throws Exception {
        List<AnnotationData> list = new ArrayList<AnnotationData>();
        AnnotationData data = new AnnotationData();
        data.setDate(DateUtils.parseDate("2020-01-01 01:01:01"));
        data.setNumber(99.99);
        data.setIgnore("忽略");
        data.setTransientString("忽略");
        list.add(data);
        return list;
    }
}
