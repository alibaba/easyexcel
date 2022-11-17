package com.alibaba.easyexcel.test.core.compatibility;

import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Compatible with some special files
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class CompatibilityTest {

    @Test
    public void t01() {
        // https://github.com/alibaba/easyexcel/issues/2236
        List<Map<Integer, Object>> list = EasyExcel.read(TestFileUtil.getPath() + "compatibility/t01.xls").sheet()
            .doReadSync();
        Assert.assertEquals(2, list.size());
        Map<Integer, Object> row1 = list.get(1);
        Assert.assertEquals("Q235(碳钢)", row1.get(0));
    }

    @Test
    public void t02() {
        // Exist in `sharedStrings.xml` `x:t` start tag, need to be compatible
        List<Map<Integer, Object>> list = EasyExcel.read(TestFileUtil.getPath() + "compatibility/t02.xlsx").sheet()
            .headRowNumber(0).doReadSync();
        log.info("data:{}", JSON.toJSONString(list));
        Assert.assertEquals(3, list.size());
        Map<Integer, Object> row2 = list.get(2);
        Assert.assertEquals("1，2-戊二醇", row2.get(2));
    }
}
