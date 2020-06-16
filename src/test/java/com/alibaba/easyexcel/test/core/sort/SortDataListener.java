package com.alibaba.easyexcel.test.core.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 */
public class SortDataListener extends AnalysisEventListener<SortData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SortDataListener.class);
    List<SortData> list = new ArrayList<SortData>();


    @Override
    public void invoke(SortData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        SortData sortData = list.get(0);
        Assert.assertEquals("column1", sortData.getColumn1());
        Assert.assertEquals("column2", sortData.getColumn2());
        Assert.assertEquals("column3", sortData.getColumn3());
        Assert.assertEquals("column4", sortData.getColumn4());
        Assert.assertEquals("column5", sortData.getColumn5());
        Assert.assertEquals("column6", sortData.getColumn6());
    }
}
