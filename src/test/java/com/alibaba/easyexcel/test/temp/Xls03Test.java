package com.alibaba.easyexcel.test.temp;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class Xls03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Xls03Test.class);

    @Test
    public void test() {
        List<Object> list = EasyExcel.read("D:\\test\\8.xls").sheet().doReadSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }
}
