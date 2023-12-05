package com.alibaba.easyexcel.test.core.simple;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class SimpleDataSheetNameListener extends AnalysisEventListener<SimpleData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataSheetNameListener.class);
    List<SimpleData> list = new ArrayList<SimpleData>();

    @Override
    public void invoke(SimpleData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        Assertions.assertEquals(list.get(0).getName(), "张三");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
