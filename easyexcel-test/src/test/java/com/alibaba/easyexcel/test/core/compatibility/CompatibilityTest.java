package com.alibaba.easyexcel.test.core.compatibility;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.Ehcache;
import com.alibaba.excel.enums.ReadDefaultReturnEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.TempFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Compatible with some special files
 *
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
@Slf4j
public class CompatibilityTest {

    @Test
    public void t01() {
        // https://github.com/alibaba/easyexcel/issues/2236
        List<Map<Integer, Object>> list = EasyExcel.read(TestFileUtil.getPath() + "compatibility/t01.xls").sheet()
            .doReadSync();
        Assertions.assertEquals(2, list.size());
        Map<Integer, Object> row1 = list.get(1);
        Assertions.assertEquals("Q235(碳钢)", row1.get(0));
    }

    @Test
    public void t02() {
        // Exist in `sharedStrings.xml` `x:t` start tag, need to be compatible
        List<Map<Integer, Object>> list = EasyExcel.read(TestFileUtil.getPath() + "compatibility/t02.xlsx").sheet()
            .headRowNumber(0).doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals(3, list.size());
        Map<Integer, Object> row2 = list.get(2);
        Assertions.assertEquals("1，2-戊二醇", row2.get(2));
    }

    @Test
    public void t03() {
        // In the presence of the first line of a lot of null columns, ignore null columns
        List<Map<Integer, Object>> list = EasyExcel.read(TestFileUtil.getPath() + "compatibility/t03.xlsx").sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals(1, list.size());
        Map<Integer, Object> row0 = list.get(0);
        Assertions.assertEquals(12, row0.size());
    }

    @Test
    public void t04() {
        // Exist in `sheet1.xml` `ns2:t` start tag, need to be compatible
        List<Map<Integer, Object>> list = EasyExcel.read(TestFileUtil.getPath() + "compatibility/t04.xlsx").sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals(56, list.size());
        Map<Integer, Object> row0 = list.get(0);
        Assertions.assertEquals("QQSJK28F152A012242S0081", row0.get(5));
    }

    @Test
    public void t05() {
        // https://github.com/alibaba/easyexcel/issues/1956
        // Excel read date needs to be rounded
        List<Map<Integer, String>> list = EasyExcel
            .read(TestFileUtil.getPath() + "compatibility/t05.xlsx")
            .sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals("2023-01-01 00:00:00", list.get(0).get(0));
        Assertions.assertEquals("2023-01-01 00:00:00", list.get(1).get(0));
        Assertions.assertEquals("2023-01-01 00:00:00", list.get(2).get(0));
        Assertions.assertEquals("2023-01-01 00:00:01", list.get(3).get(0));
        Assertions.assertEquals("2023-01-01 00:00:01", list.get(4).get(0));
    }

    @Test
    public void t06() {
        // Keep error precision digital format
        List<Map<Integer, String>> list = EasyExcel
            .read(TestFileUtil.getPath() + "compatibility/t06.xlsx")
            .headRowNumber(0)
            .sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals("2087.03", list.get(0).get(2));
    }

    @Test
    public void t07() {
        // https://github.com/alibaba/easyexcel/issues/2805
        // Excel read date needs to be rounded
        List<Map<Integer, Object>> list = EasyExcel
            .read(TestFileUtil.getPath() + "compatibility/t07.xlsx")
            .readDefaultReturn(ReadDefaultReturnEnum.ACTUAL_DATA)
            .sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals(0, new BigDecimal("24.1998124").compareTo((BigDecimal)list.get(0).get(11)));

        list = EasyExcel
            .read(TestFileUtil.getPath() + "compatibility/t07.xlsx")
            .sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals("24.20", list.get(0).get(11));
    }

    @Test
    public void t08() {
        // https://github.com/alibaba/easyexcel/issues/2693
        // Temporary files may be deleted if there is no operation for a long time, so they need to be recreated.
        File file = TestFileUtil.createNewFile("compatibility/t08.xlsx");
        EasyExcel.write(file, SimpleData.class)
            .sheet()
            .doWrite(data());

        List<Map<Integer, Object>> list = EasyExcel.read(file)
            .readCache(new Ehcache(null, 20))
            .sheet()
            .doReadSync();
        Assertions.assertEquals(10L, list.size());

        FileUtils.delete(new File(System.getProperty(TempFile.JAVA_IO_TMPDIR)));

        list = EasyExcel.read(file)
            .readCache(new Ehcache(null, 20))
            .sheet()
            .doReadSync();
        Assertions.assertEquals(10L, list.size());
    }

    @Test
    public void t09() {
        // `SH_x005f_x000D_Z002` exists in `ShardingString.xml` and needs to be replaced by: `SH_x000D_Z002`
        File file = TestFileUtil.readFile("compatibility/t09.xlsx");
        List<Map<Integer, Object>> list = EasyExcel.read(file)
            .headRowNumber(0)
            .sheet()
            .doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assertions.assertEquals(1, list.size());

        Assertions.assertEquals("SH_x000D_Z002", list.get(0).get(0));
    }

    private List<SimpleData> data() {
        List<SimpleData> list = new ArrayList<SimpleData>();
        for (int i = 0; i < 10; i++) {
            SimpleData simpleData = new SimpleData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
