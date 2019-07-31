package com.alibaba.easyexcel.test.core.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.easyexcel.test.core.simple.SimpleDataListener;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;

/**
 * Annotation data test
 *
 * @author zhuangjiaju
 */
public class AnnotationDataTest {

    private File file07 = TestFileUtil.createNewFile("simple07.xlsx");
    private File file03 = TestFileUtil.createNewFile("simple03.xls");



    @Test
    public void readAndWrite07() {
        EasyExcelFactory.write(file07, com.alibaba.easyexcel.test.core.simple.SimpleData.class).sheet().doWrite(data())
            .finish();
        EasyExcelFactory.read(file07, com.alibaba.easyexcel.test.core.simple.SimpleData.class,
            new com.alibaba.easyexcel.test.core.simple.SimpleDataListener()).sheet().doRead().finish();
    }

    @Test
    public void synchronousRead07() {
        // Synchronous read file
        List<Object> list = EasyExcelFactory.read(file07).head(com.alibaba.easyexcel.test.core.simple.SimpleData.class)
            .sheet().doReadSync();
        Assert.assertEquals(list.size(), 10);
        Assert.assertTrue(list.get(0) instanceof com.alibaba.easyexcel.test.core.simple.SimpleData);
        Assert.assertEquals(((com.alibaba.easyexcel.test.core.simple.SimpleData)list.get(0)).getName(), "姓名0");
    }

    @Test
    public void readAndWrite03() {
        EasyExcelFactory.write(file03, com.alibaba.easyexcel.test.core.simple.SimpleData.class).sheet().doWrite(data())
            .finish();
        EasyExcelFactory.read(file03, com.alibaba.easyexcel.test.core.simple.SimpleData.class, new SimpleDataListener())
            .sheet().doRead().finish();
    }

    @Test

    public void synchronousRead03() {
        // Synchronous read file
        List<Object> list = EasyExcelFactory.read(file03).head(com.alibaba.easyexcel.test.core.simple.SimpleData.class)
            .sheet().doReadSync();
        Assert.assertEquals(list.size(), 10);
        Assert.assertTrue(list.get(0) instanceof com.alibaba.easyexcel.test.core.simple.SimpleData);
        Assert.assertEquals(((com.alibaba.easyexcel.test.core.simple.SimpleData)list.get(0)).getName(), "姓名0");
    }

    private List<com.alibaba.easyexcel.test.core.simple.SimpleData> data() {
        List<com.alibaba.easyexcel.test.core.simple.SimpleData> list =
            new ArrayList<com.alibaba.easyexcel.test.core.simple.SimpleData>();
        for (int i = 0; i < 10; i++) {
            com.alibaba.easyexcel.test.core.simple.SimpleData simpleData = new SimpleData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
