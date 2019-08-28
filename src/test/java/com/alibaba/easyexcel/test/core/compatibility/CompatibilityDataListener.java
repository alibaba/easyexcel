package com.alibaba.easyexcel.test.core.compatibility;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.core.annotation.AnnotationDataListener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 */
public class CompatibilityDataListener extends AnalysisEventListener<List<String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationDataListener.class);
    List<List<String>> list = new ArrayList<List<String>>();

    @Override
    public void invoke(List<String> data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 10);
        List<String> data = list.get(0);
        Assert.assertEquals(data.get(0), "字符串00");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
