package com.alibaba.easyexcel.test.core.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

/**
 * Annotation data test
 *
 * @author Jiaju Zhuang
 */
public class AnnotationIndexAndNameDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("annotationIndexAndName07.xlsx");
        file03 = TestFileUtil.createNewFile("annotationIndexAndName03.xls");
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
        EasyExcel.write(file, AnnotationIndexAndNameData.class).sheet().doWrite(data());
        EasyExcel.read(file, AnnotationIndexAndNameData.class, new AnnotationIndexAndNameDataListener()).sheet()
            .doRead();
    }

    private List<AnnotationIndexAndNameData> data() {
        List<AnnotationIndexAndNameData> list = new ArrayList<AnnotationIndexAndNameData>();
        AnnotationIndexAndNameData data = new AnnotationIndexAndNameData();
        data.setIndex0("第0个");
        data.setIndex1("第1个");
        data.setIndex2("第2个");
        data.setIndex4("第4个");
        list.add(data);
        return list;
    }
}
