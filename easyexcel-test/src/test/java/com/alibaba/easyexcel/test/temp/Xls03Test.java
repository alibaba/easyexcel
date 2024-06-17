package com.alibaba.easyexcel.test.temp;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.cglib.beans.BeanMap;
import com.alibaba.excel.support.cglib.core.DebuggingClassWriter;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/

public class Xls03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Xls03Test.class);

    @Test
    public void test() {
        List<Object> list = EasyExcel.read("D:\\test\\8.xls").sheet().doReadSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test2() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,
            "/Users/zhuangjiaju/IdeaProjects/easyexcel/target");

        CamlData camlData = new CamlData();
        //camlData.setTest("test2");
        //camlData.setAEst("test3");
        //camlData.setTEST("test4");

        BeanMap beanMap = BeanMapUtils.create(camlData);

        LOGGER.info("test:{}", beanMap.get("test"));
        LOGGER.info("test:{}", beanMap.get("Test"));
        LOGGER.info("test:{}", beanMap.get("TEst"));
        LOGGER.info("test:{}", beanMap.get("TEST"));

    }
}
