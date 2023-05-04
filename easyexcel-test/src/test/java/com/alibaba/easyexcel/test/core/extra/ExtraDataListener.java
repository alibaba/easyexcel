package com.alibaba.easyexcel.test.core.extra;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson2.JSON;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class ExtraDataListener extends AnalysisEventListener<ExtraData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraData.class);

    @Override
    public void invoke(ExtraData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        LOGGER.info("extra data:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                Assertions.assertEquals("批注的内容", extra.getText());
                Assertions.assertEquals(4, (int)extra.getRowIndex());
                Assertions.assertEquals(0, (int)extra.getColumnIndex());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    Assertions.assertEquals(1, (int)extra.getRowIndex());
                    Assertions.assertEquals(0, (int)extra.getColumnIndex());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    Assertions.assertEquals(2, (int)extra.getFirstRowIndex());
                    Assertions.assertEquals(0, (int)extra.getFirstColumnIndex());
                    Assertions.assertEquals(3, (int)extra.getLastRowIndex());
                    Assertions.assertEquals(1, (int)extra.getLastColumnIndex());
                } else {
                    Assertions.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                Assertions.assertEquals(5, (int)extra.getFirstRowIndex());
                Assertions.assertEquals(0, (int)extra.getFirstColumnIndex());
                Assertions.assertEquals(6, (int)extra.getLastRowIndex());
                Assertions.assertEquals(1, (int)extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
