package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WriteEnumTest {
    public static final int TEST_SIZE = 3;
    public static final Double TEST_DOUBLE= 3.2222;
    public static final NoticeTypeEnum[] VALUES = NoticeTypeEnum.values();
    public static final int LENGTH =  NoticeTypeEnum.values().length;
    public static final Random RAND = new Random();
    @Test
    public void writeTestFixedEnum() {
        List<EnumData> list = new ArrayList<>();
        for (int i = 0; i < TEST_SIZE; i++) {
            EnumData data = new EnumData();
            data.setNotice(NoticeTypeEnum.MONTH_SETTLE);
            data.setDoubleAmount(TEST_DOUBLE);
            list.add(data);
        }
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);

    }
    @Test
    public void writeTestRandomEnum() {
        List<EnumData> list = new ArrayList<>();
        for (int i = 0; i < TEST_SIZE; i++) {
            EnumData data = new EnumData();
            data.setNotice(VALUES[RAND.nextInt(LENGTH)]);
            data.setDoubleAmount(TEST_DOUBLE);
            list.add(data);
        }
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);

    }
    @Test
    public void writeTestNegative() {
        List<EnumDataWithoutConverter> list = new ArrayList<>();
        for (int i = 0; i < TEST_SIZE; i++) {
            EnumDataWithoutConverter data = new EnumDataWithoutConverter();
            data.setCode(RAND.nextInt(LENGTH));
            data.setDoubleAmount(TEST_DOUBLE);
            list.add(data);
        }
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumDataWithoutConverter.class).sheet("template").doWrite(list);

    }


}
