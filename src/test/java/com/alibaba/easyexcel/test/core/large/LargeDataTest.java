package com.alibaba.easyexcel.test.core.large;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

/**
 *
 * @author Jiaju Zhuang
 */
public class LargeDataTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeDataTest.class);
    private static File fileFill07;
    private static File template07;
    private int i = 0;

    @BeforeClass
    public static void init() {
        fileFill07 = TestFileUtil.createNewFile("largefill07.xlsx");
        template07 = TestFileUtil.readFile("large" + File.separator + "fill.xlsx");
    }

    @Test
    public void t01Read() {
        long start = System.currentTimeMillis();
        EasyExcel.read(TestFileUtil.getPath() + "large" + File.separator + "large07.xlsx", LargeData.class,
            new LargeDataListener()).headRowNumber(2).sheet().doRead();
        LOGGER.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void t02Fill() {
        ExcelWriter excelWriter = EasyExcel.write(fileFill07).withTemplate(template07).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.fill(data(), writeSheet);
            LOGGER.info("{} fill success.", j);
        }
        excelWriter.finish();
    }

    private List<LargeData> data() {
        List<LargeData> list = new ArrayList<LargeData>();
        int size = i + 5000;
        for (; i < size; i++) {
            LargeData largeData = new LargeData();
            list.add(largeData);
            largeData.setStr1("str1-" + i);
            largeData.setStr2("str2-" + i);
            largeData.setStr3("str3-" + i);
            largeData.setStr4("str4-" + i);
            largeData.setStr5("str5-" + i);
            largeData.setStr6("str6-" + i);
            largeData.setStr7("str7-" + i);
            largeData.setStr8("str8-" + i);
            largeData.setStr9("str9-" + i);
            largeData.setStr10("str10-" + i);
            largeData.setStr11("str11-" + i);
            largeData.setStr12("str12-" + i);
            largeData.setStr13("str13-" + i);
            largeData.setStr14("str14-" + i);
            largeData.setStr15("str15-" + i);
            largeData.setStr16("str16-" + i);
            largeData.setStr17("str17-" + i);
            largeData.setStr18("str18-" + i);
            largeData.setStr19("str19-" + i);
            largeData.setStr20("str20-" + i);
            largeData.setStr21("str21-" + i);
            largeData.setStr22("str22-" + i);
            largeData.setStr23("str23-" + i);
            largeData.setStr24("str24-" + i);
            largeData.setStr25("str25-" + i);
        }
        return list;
    }
}
