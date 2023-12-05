package com.alibaba.easyexcel.test.core.annotation;

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
public class AnnotationIndexAndNameDataListener extends AnalysisEventListener<AnnotationIndexAndNameData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationIndexAndNameDataListener.class);
    List<AnnotationIndexAndNameData> list = new ArrayList<AnnotationIndexAndNameData>();

    @Override
    public void invoke(AnnotationIndexAndNameData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        AnnotationIndexAndNameData data = list.get(0);
        Assertions.assertEquals(data.getIndex0(), "第0个");
        Assertions.assertEquals(data.getIndex1(), "第1个");
        Assertions.assertEquals(data.getIndex2(), "第2个");
        Assertions.assertEquals(data.getIndex4(), "第4个");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
