package com.alibaba.easyexcel.test.core.converter;

import java.math.BigDecimal;
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
public class ConverterDataListener extends AnalysisEventListener<ConverterData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterDataListener.class);
    List<ConverterData> list = new ArrayList<ConverterData>();

    @Override
    public void invoke(ConverterData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        ConverterData data = list.get(0);
        try {
            Assert.assertEquals(DateUtils.parseDate("2020-01-01 01:01:01"), data.getDate());
        } catch (ParseException e) {
            throw new ExcelCommonException("Test Exception", e);
        }
        Assert.assertEquals(data.getBooleanData(), Boolean.TRUE);
        Assert.assertEquals(data.getBigDecimal().doubleValue(), BigDecimal.ONE.doubleValue(), 0.0);
        Assert.assertEquals((long)data.getLongData(), 1L);
        Assert.assertEquals((long)data.getIntegerData(), 1L);
        Assert.assertEquals((long)data.getShortData(), 1L);
        Assert.assertEquals((long)data.getByteData(), 1L);
        Assert.assertEquals(data.getDoubleData(), 1.0, 0.0);
        Assert.assertEquals(data.getFloatData(), (float)1.0, 0.0);
        Assert.assertEquals(data.getString(), "测试");
        Assert.assertEquals(data.getCellData().getStringValue(), "自定义");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
