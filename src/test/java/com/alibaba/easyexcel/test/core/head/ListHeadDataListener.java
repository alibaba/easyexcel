package com.alibaba.easyexcel.test.core.head;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 */
public class ListHeadDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoHeadData.class);
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        Map<Integer, String> data = list.get(0);
        Assert.assertEquals(data.get(0), "字符串0");
        Assert.assertEquals(data.get(1), "1.0");
        Assert.assertEquals(data.get(2), "2020-01-01 01:01:01");
        Assert.assertEquals(data.get(3), "额外数据");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
