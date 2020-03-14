package com.alibaba.excel.analysis.v07.handlers.sax;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.excel.analysis.v07.handlers.CountRowCellHandler;
import com.alibaba.excel.analysis.v07.handlers.ProcessResultCellHandler;
import com.alibaba.excel.analysis.v07.handlers.XlsxCellHandler;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;

/**
 * @author jipengfei
 */
public class XlsxRowHandler extends DefaultHandler {
    private XlsxReadContext xlsxReadContext;
    private static final Map<String, XlsxCellHandler> XLSX_CELL_HANDLER_MAP = new HashMap<String, XlsxCellHandler>(16);

    static {
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.DIMENSION, new CountRowCellHandler());
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.ROW_TAG, new ProcessResultCellHandler());
    }

    public XlsxRowHandler(XlsxReadContext xlsxReadContext) {
        this.xlsxReadContext = xlsxReadContext;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        XlsxCellHandler handler = XLSX_CELL_HANDLER_MAP.get(name);
        if (handler == null) {
            return;
        }
        handler.startHandle(xlsxReadContext, name, attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (rowResultHolder != null) {
            rowResultHolder.appendCurrentCellValue(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        XlsxCellHandler handler = XLSX_CELL_HANDLER_MAP.get(name);
        if (handler == null) {
            return;
        }
        handler.endHandle(xlsxReadContext, name);
    }


}
