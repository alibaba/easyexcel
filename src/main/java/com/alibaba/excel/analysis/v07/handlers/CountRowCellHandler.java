package com.alibaba.excel.analysis.v07.handlers;

import static com.alibaba.excel.constant.ExcelXmlConstants.DIMENSION;
import static com.alibaba.excel.constant.ExcelXmlConstants.DIMENSION_REF;

import org.xml.sax.Attributes;

import com.alibaba.excel.analysis.v07.XlsxCellHandler;
import com.alibaba.excel.context.AnalysisContext;

/**
 * Cell Handler
 * 
 * @author jipengfei
 */
public class CountRowCellHandler implements XlsxCellHandler {

    private final AnalysisContext analysisContext;

    public CountRowCellHandler(AnalysisContext analysisContext) {
        this.analysisContext = analysisContext;
    }

    @Override
    public boolean support(String name) {
        return DIMENSION.equals(name);
    }

    @Override
    public void startHandle(String name, Attributes attributes) {
        String d = attributes.getValue(DIMENSION_REF);
        String totalStr = d.substring(d.indexOf(":") + 1, d.length());
        String c = totalStr.toUpperCase().replaceAll("[A-Z]", "");
        analysisContext.readSheetHolder().setTotal(Integer.parseInt(c));
    }

    @Override
    public void endHandle(String name) {

    }

    @Override
    public void handleComments(String comment) {

    }

}
