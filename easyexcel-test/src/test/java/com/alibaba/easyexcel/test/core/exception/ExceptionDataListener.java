package com.alibaba.easyexcel.test.core.exception;

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
public class ExceptionDataListener extends AnalysisEventListener<ExceptionData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionData.class);
    List<ExceptionData> list = new ArrayList<ExceptionData>();

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.info("抛出异常,忽略：{}", exception.getMessage(), exception);
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return list.size() != 8;
    }

    @Override
    public void invoke(ExceptionData data, AnalysisContext context) {
        list.add(data);
        if (list.size() == 5) {
            int i = 5 / 0;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 8);
        Assertions.assertEquals(list.get(0).getName(), "姓名0");
        Assertions.assertEquals((int)(context.readSheetHolder().getSheetNo()), 0);
        Assertions.assertEquals(
            context.readSheetHolder().getExcelReadHeadProperty().getHeadMap().get(0).getHeadNameList().get(0), "姓名");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
