package com.alibaba.easyexcel.test.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author zhuangjiaju
 */
public class ReadAllConverterDataListener extends AnalysisEventListener<ReadAllConverterData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadAllConverterDataListener.class);
    List<ReadAllConverterData> list = new ArrayList<ReadAllConverterData>();

    @Override
    public void invoke(ReadAllConverterData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        ReadAllConverterData data = list.get(0);
        // try {
        // Assert.assertEquals(data.getDate(), DateUtils.parseDate("2020-01-01 01:01:01"));
        // } catch (ParseException e) {
        // throw new ExcelCommonException("Test Exception", e);
        // }
        // Assert.assertEquals(data.getBooleanData(), Boolean.TRUE);
        // Assert.assertEquals(data.getBigDecimal().doubleValue(), BigDecimal.ONE.doubleValue(), 0.0);
        // Assert.assertEquals((long)data.getLongData(), 1L);
        // Assert.assertEquals((long)data.getIntegerData(), 1L);
        // Assert.assertEquals((long)data.getShortData(), 1L);
        // Assert.assertEquals((long)data.getByteData(), 1L);
        // Assert.assertEquals(data.getDoubleData(), 1.0, 0.0);
        // Assert.assertEquals(data.getFloatData(), (float)1.0, 0.0);
        // Assert.assertEquals(data.getString(), "测试");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
