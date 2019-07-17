package com.alibaba.easyexcel.test.read.large;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.read.simple.SimpleDataListener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author zhuangjiaju
 */
public class LargeDataListener extends AnalysisEventListener<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataListener.class);
    private int count = 0;

    @Override
    public void invoke(Object object, AnalysisContext context) {
        count++;
        if (count % 100000 == 0) {
            LOGGER.info("row:{} ,mem:{},data:{}", count, Runtime.getRuntime().totalMemory(), JSON.toJSONString(object));
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}

}
