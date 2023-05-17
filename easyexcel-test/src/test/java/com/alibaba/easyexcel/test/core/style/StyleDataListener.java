package com.alibaba.easyexcel.test.core.style;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.core.simple.SimpleDataListener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class StyleDataListener extends AnalysisEventListener<StyleData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataListener.class);
    List<StyleData> list = new ArrayList<StyleData>();

    @Override
    public void invoke(StyleData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 2);
        Assertions.assertEquals(list.get(0).getString(), "字符串0");
        Assertions.assertEquals(list.get(1).getString(), "字符串1");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
