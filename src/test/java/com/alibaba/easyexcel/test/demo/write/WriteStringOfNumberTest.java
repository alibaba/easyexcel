package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WriteStringOfNumberTest {
    public static final String TEST_STRING = "3.04";
    public static final Double TEST_DOUBLE = Double.valueOf(3.04);
    public static final int TEST_INTEGER = 3;
    public static final int TEST_SIZE= 3;

    /**
     * Test for automatically string of number conversion
     */

    @Test
    public void writeTestNegative() {
        List<List<String>> header = new ArrayList<>();
        for (int i = 0; i < TEST_SIZE; i++) {
            header.add(Collections.singletonList("header" + i));
        }
        ExcelWriter build = EasyExcel.write("/Users/ryan/easyTest/test.xlsx").build();
        WriteSheet sheet = EasyExcel.writerSheet("shell0")
            .head(header)
            .build();
        List<List<Object>> writeList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        for (int i = 0; i < TEST_SIZE; i++) {
            if (i == 0) {
                list.add(TEST_STRING);
            } else if (i == 1) {
                list.add(TEST_DOUBLE);
            } else if (i == 2) {
                list.add(TEST_INTEGER);
            }
        }
        writeList.add(list);
        build.write(writeList, sheet);
        build.finish();
    }

    @Test
    public void writeTestPositive() {
        List<List<String>> header = new ArrayList<>();
        for (int i = 0; i < TEST_SIZE; i++) {
            header.add(Collections.singletonList("header" + i));
        }
        ExcelWriter build = EasyExcel.write("/Users/ryan/easyTest/test.xlsx").build();
        WriteSheet sheet = EasyExcel.writerSheet("shell0")
            .head(header)
            .build();
        List<List<Object>> writeList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        for (int i = 0; i < TEST_SIZE; i++) {
            if (i == 0) {
                list.add(TEST_INTEGER);
            } else if (i == 1) {
                list.add(TEST_DOUBLE);
            } else if (i == 2) {
                list.add(TEST_INTEGER);
            }
        }
        writeList.add(list);
        build.write(writeList, sheet);
        build.finish();
    }
}
