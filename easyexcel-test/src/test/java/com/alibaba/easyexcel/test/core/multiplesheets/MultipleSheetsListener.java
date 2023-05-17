package com.alibaba.easyexcel.test.core.multiplesheets;

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
public class MultipleSheetsListener extends AnalysisEventListener<MultipleSheetsData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSheetsListener.class);
    List<MultipleSheetsData> list = new ArrayList<MultipleSheetsData>();

    @Override
    public void invoke(MultipleSheetsData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.debug("A form is read finished.");
        Assertions.assertEquals(list.get(0).getTitle(), "表1数据");
        LOGGER.debug("All row:{}", JSON.toJSONString(list));
    }

    public List<MultipleSheetsData> getList() {
        return list;
    }

    public void setList(List<MultipleSheetsData> list) {
        this.list = list;
    }
}
