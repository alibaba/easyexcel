package com.alibaba.easyexcel.test.core.extra;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;

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
                Assert.assertEquals("批注的内容", extra.getText());
                Assert.assertEquals(4, (int)extra.getRowIndex());
                Assert.assertEquals(0, (int)extra.getColumnIndex());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    Assert.assertEquals(1, (int)extra.getRowIndex());
                    Assert.assertEquals(0, (int)extra.getColumnIndex());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    Assert.assertEquals(2, (int)extra.getFirstRowIndex());
                    Assert.assertEquals(0, (int)extra.getFirstColumnIndex());
                    Assert.assertEquals(3, (int)extra.getLastRowIndex());
                    Assert.assertEquals(1, (int)extra.getLastColumnIndex());
                } else {
                    Assert.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                Assert.assertEquals(5, (int)extra.getFirstRowIndex());
                Assert.assertEquals(0, (int)extra.getFirstColumnIndex());
                Assert.assertEquals(6, (int)extra.getLastRowIndex());
                Assert.assertEquals(1, (int)extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
