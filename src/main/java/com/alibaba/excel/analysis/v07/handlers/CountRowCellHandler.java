package com.alibaba.excel.analysis.v07.handlers;

import static com.alibaba.excel.constant.ExcelXmlConstants.DIMENSION_REF;

import org.xml.sax.Attributes;

import com.alibaba.excel.context.xlsx.XlsxReadContext;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class CountRowCellHandler implements XlsxCellHandler {


    @Override
    public void startHandle(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        String d = attributes.getValue(DIMENSION_REF);
        String totalStr = d.substring(d.indexOf(":") + 1, d.length());
        String c = totalStr.toUpperCase().replaceAll("[A-Z]", "");
        xlsxReadContext.readSheetHolder().setApproximateTotalRowNumber(Integer.parseInt(c));
    }

    @Override
    public void endHandle(XlsxReadContext xlsxReadContext, String name) {
    }

}
