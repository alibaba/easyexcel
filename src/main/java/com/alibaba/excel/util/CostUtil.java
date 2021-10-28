package com.alibaba.excel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CostUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostUtil.class);
    private static long start = 0;

    public static int count = 0;
    public static int count2 = 0;


    public static void start() {
        start = System.currentTimeMillis();
        LOGGER.info("开始执行");
    }

    public static void print(String print) {
        LOGGER.info(print + "为止耗时：{}", System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
    }
}
