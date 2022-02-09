package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.util.PositionUtils;

import org.xml.sax.Attributes;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class CountTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        String d = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_REF);
        String totalStr = d.substring(d.indexOf(":") + 1);
        xlsxReadContext.readSheetHolder().setApproximateTotalRowNumber(PositionUtils.getRow(totalStr) + 1);
    }

}
