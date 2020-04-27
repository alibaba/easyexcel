package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.context.xlsx.XlsxReadContext;
import org.xml.sax.Attributes;

public class ImageAnchorFromTagHandler extends XlsxTagHandler {

    @Override
    public boolean support(XlsxReadContext xlsxReadContext) {
        return false;
    }

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {

    }

    @Override
    public void endElement(XlsxReadContext xlsxReadContext, String name) {

    }

    @Override
    public void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length) {

    }
}
