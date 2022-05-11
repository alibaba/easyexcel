package com.alibaba.easyexcel.test.core.compatibility;

import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

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
}
