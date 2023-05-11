package com.alibaba.easyexcel.test.core.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class SimpleDataListener extends AnalysisEventListener<SimpleData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataListener.class);
    List<SimpleData> list = new ArrayList<SimpleData>();

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.debug("Head is:{}", JSON.toJSONString(headMap));
        Assertions.assertEquals(headMap.get(0), "姓名");
    }

    @Override
    public void invoke(SimpleData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 10);
        Assertions.assertEquals(list.get(0).getName(), "姓名0");
        Assertions.assertEquals((int)(context.readSheetHolder().getSheetNo()), 0);
        Assertions.assertEquals(
            context.readSheetHolder().getExcelReadHeadProperty().getHeadMap().get(0).getHeadNameList().get(0), "姓名");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
