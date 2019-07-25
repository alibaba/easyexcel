package com.alibaba.easyexcel.test.core.simple;

import java.io.File;

import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;

/**
 * Simple data test
 * 
 * @author zhuangjiaju
 */
public class SimpleDataTest {

    @Test
    public void read() {
        EasyExcelFactory.read(TestFileUtil.getPath() + "simple" + File.separator + "simple07Test.xlsx",
            SimpleData.class, new SimpleDataListener()).sheet().doRead().finish();
    }
}
