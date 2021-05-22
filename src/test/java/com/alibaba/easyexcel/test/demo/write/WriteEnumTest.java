package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WriteEnumTest {
    public static final Double TEST_DOUBLE= 3.2222;
    public static final NoticeTypeEnum[] VALUES = NoticeTypeEnum.values();
    public static final int LENGTH =  NoticeTypeEnum.values().length;
    public static final Random RAND = new Random();
    public static final int RANDOM_SIZE = RAND.nextInt(20);
    @Test
    public void writeTestPositiveFixedEnum() {
        List<EnumData> list = new ArrayList<>();
        EnumData data1 = new EnumData();
        data1.setNotice(NoticeTypeEnum.MONTH_SETTLE);
        data1.setDoubleAmount(TEST_DOUBLE);
        EnumData data2 = new EnumData();
        data2.setNotice(NoticeTypeEnum.ARREARS_REMINDER);
        data2.setDoubleAmount(TEST_DOUBLE);
        EnumData data3 = new EnumData();
        data3.setNotice(NoticeTypeEnum.ARREARS_OFF);
        data3.setDoubleAmount(TEST_DOUBLE);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }
    @Test
    public void writeTestPositiveRandomSizeEnum() {
        List<EnumData> list = new ArrayList<>();
        for(int i = 0; i<RANDOM_SIZE;i++){
            EnumData data = new EnumData();
            data.setNotice(VALUES[RAND.nextInt(LENGTH)]);
            data.setDoubleAmount(TEST_DOUBLE);
            list.add(data);
        }
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }

    @Test
    public void writeTestPositiveEmptyData() {
        List<EnumData> list = new ArrayList<>();
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }


    @Test
    public void writeTestPositiveRandomEnum() {
        List<EnumData> list = new ArrayList<>();
        EnumData data1 = new EnumData();
        data1.setNotice(VALUES[RAND.nextInt(LENGTH)]);
        data1.setDoubleAmount(TEST_DOUBLE);
        EnumData data2 = new EnumData();
        data2.setNotice(VALUES[RAND.nextInt(LENGTH)]);
        data2.setDoubleAmount(TEST_DOUBLE);
        EnumData data3 = new EnumData();
        data3.setNotice(VALUES[RAND.nextInt(LENGTH)]);
        data3.setDoubleAmount(TEST_DOUBLE);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }
    @Test
    public void writeTestNegativeFixedEnum() {
        List<EnumDataWithoutConverter> list = new ArrayList<>();
        EnumDataWithoutConverter data1 = new EnumDataWithoutConverter();
        data1.setCode(1);
        data1.setDoubleAmount(TEST_DOUBLE);

        EnumDataWithoutConverter data2 = new EnumDataWithoutConverter();
        data1.setCode(2);
        data1.setDoubleAmount(TEST_DOUBLE);
        EnumDataWithoutConverter data3 = new EnumDataWithoutConverter();
        data1.setCode(3);
        data1.setDoubleAmount(TEST_DOUBLE);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumDataWithoutConverter.class).sheet("template").doWrite(list);

    }
    @Test
    public void writeTestNegativeFixedRandom() {
        List<EnumDataWithoutConverter> list = new ArrayList<>();
        EnumDataWithoutConverter data1 = new EnumDataWithoutConverter();
        data1.setCode(RAND.nextInt(LENGTH));
        data1.setDoubleAmount(TEST_DOUBLE);

        EnumDataWithoutConverter data2 = new EnumDataWithoutConverter();
        data1.setCode(RAND.nextInt(LENGTH));
        data1.setDoubleAmount(TEST_DOUBLE);

        EnumDataWithoutConverter data3 = new EnumDataWithoutConverter();
        data1.setCode(RAND.nextInt(LENGTH));
        data1.setDoubleAmount(TEST_DOUBLE);

        list.add(data1);
        list.add(data2);
        list.add(data3);
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumDataWithoutConverter.class).sheet("template").doWrite(list);
    }


    @Test
    public void writeTestNegativeRandomSizeEnum() {
        List<EnumDataWithoutConverter> list = new ArrayList<>();
        for(int i = 0; i<RANDOM_SIZE;i++){
            EnumDataWithoutConverter data = new EnumDataWithoutConverter();
            data.setCode(RAND.nextInt(LENGTH));
            data.setDoubleAmount(TEST_DOUBLE);
            list.add(data);
        }
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }

    @Test
    public void writeTestNegativeEmptyData() {
        List<EnumDataWithoutConverter> list = new ArrayList<>();
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }

    @Test
    public void writeTestNegativeSameData() {
        List<EnumDataWithoutConverter> list = new ArrayList<>();
        EnumDataWithoutConverter data = new EnumDataWithoutConverter();
        data.setCode(1);
        data.setDoubleAmount(TEST_DOUBLE);
        list.add(data);
        list.add(data);
        list.add(data);
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }
    @Test
    public void writeTestPositiveSameData() {
        List<EnumData> list = new ArrayList<>();
        EnumData data = new EnumData();
        data.setNotice(NoticeTypeEnum.MONTH_SETTLE);
        data.setDoubleAmount(TEST_DOUBLE);
        list.add(data);
        list.add(data);
        list.add(data);
        String fileName = "/tmp/"+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, EnumData.class).sheet("template").doWrite(list);
    }


}
