package com.alibaba.easyexcel.test.core.nomodel;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.ReadDefaultReturnEnum;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class NoModelDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File fileRepeat07;
    private static File fileRepeat03;
    private static File fileRepeatCsv;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("noModel07.xlsx");
        file03 = TestFileUtil.createNewFile("noModel03.xls");
        fileCsv = TestFileUtil.createNewFile("noModelCsv.csv");
        fileRepeat07 = TestFileUtil.createNewFile("noModelRepeat07.xlsx");
        fileRepeat03 = TestFileUtil.createNewFile("noModelRepeat03.xls");
        fileRepeatCsv = TestFileUtil.createNewFile("noModelRepeatCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07, fileRepeat07, false);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03, fileRepeat03, false);
    }

    @Test
    public void t03ReadAndWriteCsv() throws Exception {
        readAndWrite(fileCsv, fileRepeatCsv, true);
    }

    private void readAndWrite(File file, File fileRepeat, boolean isCsv) throws Exception {
        EasyExcel.write(file).sheet().doWrite(data());
        List<Map<Integer, String>> result = EasyExcel.read(file).headRowNumber(0).sheet().doReadSync();
        Assert.assertEquals(10, result.size());
        Map<Integer, String> data10 = result.get(9);
        Assert.assertEquals("string19", data10.get(0));
        Assert.assertEquals("109", data10.get(1));
        Assert.assertEquals("2020-01-01 01:01:01", data10.get(2));

        List<Map<Integer, Object>> actualDataList = EasyExcel.read(file)
            .headRowNumber(0)
            .readDefaultReturn(ReadDefaultReturnEnum.ACTUAL_DATA)
            .sheet()
            .doReadSync();
        log.info("actualDataList:{}", JSON.toJSONString(actualDataList));
        Assert.assertEquals(10, actualDataList.size());
        Map<Integer, Object> actualData10 = actualDataList.get(9);
        Assert.assertEquals("string19", actualData10.get(0));
        if (isCsv) {
            //  CSV only string type
            Assert.assertEquals("109", actualData10.get(1));
            Assert.assertEquals("2020-01-01 01:01:01", actualData10.get(2));
        } else {
            Assert.assertEquals(0, new BigDecimal("109").compareTo((BigDecimal)actualData10.get(1)));
            Assert.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1, 1), actualData10.get(2));
        }

        List<Map<Integer, ReadCellData<?>>> readCellDataList = EasyExcel.read(file)
            .headRowNumber(0)
            .readDefaultReturn(ReadDefaultReturnEnum.READ_CELL_DATA)
            .sheet()
            .doReadSync();
        log.info("readCellDataList:{}", JSON.toJSONString(readCellDataList));
        Assert.assertEquals(10, readCellDataList.size());
        Map<Integer, ReadCellData<?>> readCellData10 = readCellDataList.get(9);
        Assert.assertEquals("string19", readCellData10.get(0).getData());
        if (isCsv) {
            //  CSV only string type
            Assert.assertEquals("109", readCellData10.get(1).getData());
            Assert.assertEquals("2020-01-01 01:01:01", readCellData10.get(2).getData());
        } else {
            Assert.assertEquals(0, new BigDecimal("109").compareTo((BigDecimal)readCellData10.get(1).getData()));
            Assert.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1, 1), readCellData10.get(2).getData());
        }

        EasyExcel.write(fileRepeat).sheet().doWrite(result);
        result = EasyExcel.read(fileRepeat).headRowNumber(0).sheet().doReadSync();
        Assert.assertEquals(10, result.size());
        data10 = result.get(9);
        Assert.assertEquals("string19", data10.get(0));
        Assert.assertEquals("109", data10.get(1));
        Assert.assertEquals("2020-01-01 01:01:01", data10.get(2));
    }

    private List<List<Object>> data() throws Exception {
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<>();
            data.add("string1" + i);
            data.add(100 + i);
            data.add(DateUtils.parseDate("2020-01-01 01:01:01"));
            list.add(data);
        }
        return list;
    }
}
