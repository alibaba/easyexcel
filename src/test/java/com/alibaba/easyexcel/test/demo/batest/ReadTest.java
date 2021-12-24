package com.alibaba.easyexcel.test.demo.batest;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.github.byteautumn.excel.EasyExcel2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * 简单的测试下
 *
 * @author byte.autumn
 */
@Ignore
@Slf4j
public class ReadTest {

    @Test
    public void simpleRead() {
        // 简单的读一下

        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo1.xlsx";

        List<Object> readRes = EasyExcel.read(fileName).sheet("Sheet3").headRowNumber(-1).doReadSync();

        readRes.forEach(System.out::println);
    }

    @Test
    public void testRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo1.xlsx";

        List<Object> readRes = EasyExcel2.read2(fileName, null, null).doReadAllSync();

        readRes.forEach(System.out::println);
    }
}
