package com.alibaba.easyexcel.test.core.skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SkipDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("skip.xlsx");
        file03 = TestFileUtil.createNewFile("skip.xls");
        fileCsv = TestFileUtil.createNewFile("skip.csv");
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
        Assert.assertThrows(ExcelGenerateException.class, () -> readAndWrite(fileCsv));
    }

    private void readAndWrite(File file) {
        try (ExcelWriter excelWriter = EasyExcel.write(file, SimpleData.class).build();) {
            WriteSheet writeSheet0 = EasyExcel.writerSheet(0, "第一个").build();
            WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "第二个").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet(2, "第三个").build();
            WriteSheet writeSheet3 = EasyExcel.writerSheet(3, "第四个").build();
            excelWriter.write(data("name1"), writeSheet0);
            excelWriter.write(data("name2"), writeSheet1);
            excelWriter.write(data("name3"), writeSheet2);
            excelWriter.write(data("name4"), writeSheet3);
        }

        List<SkipData> list = EasyExcel.read(file, SkipData.class, null).sheet("第二个").doReadSync();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("name2", list.get(0).getName());

        SyncReadListener syncReadListener = new SyncReadListener();
        try (ExcelReader excelReader = EasyExcel.read(file, SkipData.class, null).registerReadListener(syncReadListener)
            .build()) {
            ReadSheet readSheet1 = EasyExcel.readSheet("第二个").build();
            ReadSheet readSheet3 = EasyExcel.readSheet("第四个").build();
            excelReader.read(readSheet1, readSheet3);
            List<Object> syncList = syncReadListener.getList();
            Assert.assertEquals(2, syncList.size());
            Assert.assertEquals("name2", ((SkipData)syncList.get(0)).getName());
            Assert.assertEquals("name4", ((SkipData)syncList.get(1)).getName());
        }
    }

    private List<SkipData> data(String name) {
        List<SkipData> list = new ArrayList<SkipData>();
        SkipData data = new SkipData();
        data.setName(name);
        list.add(data);
        return list;
    }
}
