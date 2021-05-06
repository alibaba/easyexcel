package com.alibaba.easyexcel.test.core.celldata;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class CellDataDataListener extends AnalysisEventListener<CellDataReadData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CellDataDataListener.class);
    List<CellDataReadData> list = new ArrayList<>();

    @Override
    public void invoke(CellDataReadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        CellDataReadData cellDataData = list.get(0);

        Assert.assertEquals(cellDataData.getDate().getData(), "2020年01月01日");
        Assert.assertEquals((long)cellDataData.getInteger1().getData(), 2L);
        Assert.assertEquals((long)cellDataData.getInteger2(), 2L);
        Assert.assertEquals(cellDataData.getFormulaValue().getFormulaData().getFormulaValue(), "B2+C2");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
