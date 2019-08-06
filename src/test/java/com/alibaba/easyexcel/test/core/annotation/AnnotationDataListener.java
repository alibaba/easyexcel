package com.alibaba.easyexcel.test.core.annotation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 */
public class AnnotationDataListener extends AnalysisEventListener<AnnotationData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationDataListener.class);
    List<AnnotationData> list = new ArrayList<AnnotationData>();

    @Override
    public void invoke(AnnotationData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        AnnotationData data = list.get(0);
        try {
            Assert.assertEquals(data.getDate(), DateUtils.parseDate("2020-01-01 01:01:01"));
        } catch (ParseException e) {
            throw new ExcelCommonException("Test Exception", e);
        }
        Assert.assertEquals(data.getNumber(), 99.99, 0.00);
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
