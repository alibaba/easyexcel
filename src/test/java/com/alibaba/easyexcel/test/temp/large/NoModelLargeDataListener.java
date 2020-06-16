package com.alibaba.easyexcel.test.temp.large;

import java.util.Map;

import org.apache.poi.ss.formula.functions.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 */
public class NoModelLargeDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoModelLargeDataListener.class);
    private int count = 0;

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (count == 0) {
            LOGGER.info("First row:{}", JSON.toJSONString(data));
        }
        count++;
        if (count % 100000 == 0) {
            LOGGER.info("Already read:{}", count);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("Large row count:{}", count);
    }
}
