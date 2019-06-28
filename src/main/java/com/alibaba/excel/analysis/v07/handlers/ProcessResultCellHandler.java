package com.alibaba.excel.analysis.v07.handlers;

import org.xml.sax.Attributes;
import static com.alibaba.excel.constant.ExcelXmlConstants.ROW_TAG;
import com.alibaba.excel.analysis.v07.XlsxCellHandler;
import com.alibaba.excel.analysis.v07.XlsxRowResultHolder;
import com.alibaba.excel.event.AnalysisEventRegistryCenter;
import com.alibaba.excel.event.EachRowAnalysisFinishEvent;

public class ProcessResultCellHandler implements XlsxCellHandler {
    private AnalysisEventRegistryCenter registerCenter;
    private XlsxRowResultHolder rowResultHandler;

    public ProcessResultCellHandler(AnalysisEventRegistryCenter registerCenter,
                    XlsxRowResultHolder rowResultHandler) {
        this.registerCenter = registerCenter;
        this.rowResultHandler = rowResultHandler;
    }

    @Override
    public boolean support(String name) {
        return ROW_TAG.equals(name);
    }

    @Override
    public void startHandle(String name, Attributes attributes) {

    }

    @Override
    public void endHandle(String name) {
        registerCenter.notify(new EachRowAnalysisFinishEvent(rowResultHandler.getCurRowContent(),
                        rowResultHandler.getColumnSize()));
        rowResultHandler.clearResult();
    }

}
