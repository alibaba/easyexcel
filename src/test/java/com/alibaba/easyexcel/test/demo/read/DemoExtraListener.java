package com.alibaba.easyexcel.test.demo.read;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;

/**
 * 读取单元格的批注
 *
 * @author: kaiux
 * @date: 2019-10-23 14:10
 **/
public class DemoExtraListener extends AnalysisEventListener<DemoExtraData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);

    @Override
    public void invoke(DemoExtraData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        LOGGER.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                LOGGER.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
                    extra.getText());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    LOGGER.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
                        extra.getColumnIndex(), extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    LOGGER.info(
                        "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
                            + "内容是:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                        extra.getLastColumnIndex(), extra.getText());
                } else {
                    Assert.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                LOGGER.info(
                    "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                    extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                    extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
