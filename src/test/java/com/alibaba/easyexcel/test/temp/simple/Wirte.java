package com.alibaba.easyexcel.test.temp.simple;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class Wirte {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wirte.class);

    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "ttttttttt11" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName).sheet("模板").doWrite(data());
    }

    private List<List<Object>> data() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            List<Object> list1 = new ArrayList<Object>();

            list1.add("字符串" + i);
            list1.add(new Date());
            list1.add(0.56);
            list.add(list1);
        }
        return list;
    }
}
