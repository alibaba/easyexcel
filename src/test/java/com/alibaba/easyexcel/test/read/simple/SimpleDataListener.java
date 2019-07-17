package com.alibaba.easyexcel.test.read.simple;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author zhuangjiaju
 */
public class SimpleDataListener extends AnalysisEventListener<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataListener.class);

    List<Object> list = new ArrayList<Object>();

    @Override
    public void invoke(Object object, AnalysisContext context) {
        list.add(object);
        LOGGER.info("data:{}", JSON.toJSONString(object));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        assert list.size() == 10;
    }
}
