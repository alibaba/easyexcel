package com.alibaba.easyexcel.test.core.large;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class LargeDataListener extends AnalysisEventListener<LargeData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeDataListener.class);
    private int count = 0;

    @Override
    public void invoke(LargeData data, AnalysisContext context) {
        if (count == 0) {
            LOGGER.info("First row:{}", JSON.toJSONString(data));
        }
        count++;
        if (count % 100000 == 0) {
            LOGGER.info("Already read:{},{}", count, JSON.toJSONString(data));
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("Large row count:{}", count);
        if (context.readWorkbookHolder().getExcelType() != ExcelTypeEnum.CSV) {
            Assert.assertEquals(count, 464509);
        } else {
            Assert.assertEquals(count, 499999);
        }
    }
}
