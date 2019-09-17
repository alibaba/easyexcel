package com.alibaba.easyexcel.test.temp.simple;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class HgTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HgTest.class);

    @Test
    public void hh() throws IOException {
        List<Object> list =
            EasyExcel.read(new FileInputStream("D:\\test\\number.xlsx")).headRowNumber(0).sheet().doReadSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void hh2() throws IOException {
        EasyExcel.read(new FileInputStream("D:\\test\\商户不匹配工单信息收集表格.xlsx"))
            .registerReadListener(new HgListener()).headRowNumber(0).sheet().doRead();
    }

}
