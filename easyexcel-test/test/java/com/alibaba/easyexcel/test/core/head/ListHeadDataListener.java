package com.alibaba.easyexcel.test.core.head;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class ListHeadDataListener implements ReadListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoHeadData.class);
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Assert.assertNotNull(context.readRowHolder().getRowIndex());
        headMap.forEach((key, value) -> {
            Assert.assertEquals(value.getRowIndex(), context.readRowHolder().getRowIndex());
            Assert.assertEquals(value.getColumnIndex(), key);
        });
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 1);
        Map<Integer, String> data = list.get(0);
        Assert.assertEquals("字符串0", data.get(0));
        Assert.assertEquals("1", data.get(1));
        Assert.assertEquals("2020-01-01 01:01:01", data.get(2));
        Assert.assertEquals("额外数据", data.get(3));
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
