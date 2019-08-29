package com.alibaba.easyexcel.test.temp;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class Lock2Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lock2Test.class);

    @Test
    public void test() throws Exception {
        File file = new File("D:\\test\\珠海2.xlsx");

        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
        LOGGER.info("文件状态：{}", file.exists());
        file.delete();
        Thread.sleep(500 * 1000);
    }

    @Test
    public void test2() throws Exception {
        File file = new File("D:\\test\\converter03.xls");

        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
        LOGGER.info("文件状态：{}", file.exists());
        file.delete();
        Thread.sleep(500 * 1000);
    }


}
