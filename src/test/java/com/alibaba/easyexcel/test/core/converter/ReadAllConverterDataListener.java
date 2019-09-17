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
        Assert.assertEquals(data.getBigDecimalBoolean().doubleValue(), BigDecimal.ONE.doubleValue(), 0.0);
        Assert.assertEquals(data.getBigDecimalNumber().doubleValue(), BigDecimal.ONE.doubleValue(), 0.0);
        Assert.assertEquals(data.getBigDecimalString().doubleValue(), BigDecimal.ONE.doubleValue(), 0.0);
        Assert.assertTrue(data.getBooleanBoolean());
        Assert.assertTrue(data.getBooleanNumber());
        Assert.assertTrue(data.getBooleanString());
        Assert.assertEquals((long)data.getByteBoolean(), 1L);
        Assert.assertEquals((long)data.getByteNumber(), 1L);
        Assert.assertEquals((long)data.getByteString(), 1L);
        try {
            Assert.assertEquals(data.getDateNumber(), DateUtils.parseDate("2020-01-01 01:01:01"));
            Assert.assertEquals(data.getDateString(), DateUtils.parseDate("2020-01-01 01:01:01"));
        } catch (ParseException e) {
            throw new ExcelCommonException("Test Exception", e);
        }
        Assert.assertEquals(data.getDoubleBoolean(), 1.0, 0.0);
        Assert.assertEquals(data.getDoubleNumber(), 1.0, 0.0);
        Assert.assertEquals(data.getDoubleString(), 1.0, 0.0);
        Assert.assertEquals(data.getFloatBoolean(), (float)1.0, 0.0);
        Assert.assertEquals(data.getFloatNumber(), (float)1.0, 0.0);
        Assert.assertEquals(data.getFloatString(), (float)1.0, 0.0);
        Assert.assertEquals((long)data.getIntegerBoolean(), 1L);
        Assert.assertEquals((long)data.getIntegerNumber(), 1L);
        Assert.assertEquals((long)data.getIntegerString(), 1L);
        Assert.assertEquals((long)data.getLongBoolean(), 1L);
        Assert.assertEquals((long)data.getLongNumber(), 1L);
        Assert.assertEquals((long)data.getLongString(), 1L);
        Assert.assertEquals((long)data.getShortBoolean(), 1L);
        Assert.assertEquals((long)data.getShortNumber(), 1L);
        Assert.assertEquals((long)data.getShortString(), 1L);
        Assert.assertEquals(data.getStringBoolean(), "true");
        Assert.assertEquals(data.getStringString(), "测试");
        Assert.assertEquals(data.getStringError(), "#VALUE!");
        Assert.assertEquals(data.getStringNumberDate(), "2020-01-01 01:01:01");
        double doubleStringFormulaNumber = new BigDecimal(data.getStringFormulaNumber()).doubleValue();
        Assert.assertEquals(doubleStringFormulaNumber, 2.0, 0.0);
        Assert.assertEquals(data.getStringFormulaString(), "1测试");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
