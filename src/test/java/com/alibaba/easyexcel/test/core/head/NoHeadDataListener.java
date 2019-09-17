package com.alibaba.easyexcel.test.core.head;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 */
public class NoHeadDataListener extends AnalysisEventListener<NoHeadData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoHeadData.class);
    List<NoHeadData> list = new ArrayList<NoHeadData>();

    @Override
    public void invoke(NoHeadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        NoHeadData data = list.get(0);
        Assert.assertEquals(data.getString(), "字符串0");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
