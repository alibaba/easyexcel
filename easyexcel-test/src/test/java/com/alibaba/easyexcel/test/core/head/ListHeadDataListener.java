package com.alibaba.easyexcel.test.core.head;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Assertions;
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
        Assertions.assertNotNull(context.readRowHolder().getRowIndex());
        headMap.forEach((key, value) -> {
            Assertions.assertEquals(value.getRowIndex(), context.readRowHolder().getRowIndex());
            Assertions.assertEquals(value.getColumnIndex(), key);
        });
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        Map<Integer, String> data = list.get(0);
        Assertions.assertEquals("字符串0", data.get(0));
        Assertions.assertEquals("1", data.get(1));
        Assertions.assertEquals("2020-01-01 01:01:01", data.get(2));
        Assertions.assertEquals("额外数据", data.get(3));
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
