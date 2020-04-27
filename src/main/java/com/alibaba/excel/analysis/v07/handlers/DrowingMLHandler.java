package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.constant.ExcelXmlConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class DrowingMLHandler extends DefaultHandler {
    //用一个新类替换掉XlsxTagHandler
    private static final Map<String, XlsxTagHandler> XLSX_CELL_HANDLER_MAP = new HashMap<String, XlsxTagHandler>(32);

    static {
        ImageAnchorFromTagHandler imageAnchorFromTagHandler = new ImageAnchorFromTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.CELL_FORMULA_TAG, imageAnchorFromTagHandler);

        CellInlineStringValueTagHandler cellInlineStringValueTagHandler = new CellInlineStringValueTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.CELL_INLINE_STRING_VALUE_TAG, cellInlineStringValueTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_CELL_INLINE_STRING_VALUE_TAG, cellInlineStringValueTagHandler);
        CellTagHandler cellTagHandler = new CellTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.CELL_TAG, cellTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_CELL_TAG, cellTagHandler);
        CellValueTagHandler cellValueTagHandler = new CellValueTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.CELL_VALUE_TAG, cellValueTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_CELL_VALUE_TAG, cellValueTagHandler);
        CountTagHandler countTagHandler = new CountTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.DIMENSION_TAG, countTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_DIMENSION_TAG, countTagHandler);
        HyperlinkTagHandler hyperlinkTagHandler = new HyperlinkTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.HYPERLINK_TAG, hyperlinkTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_HYPERLINK_TAG, hyperlinkTagHandler);
        MergeCellTagHandler mergeCellTagHandler = new MergeCellTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.MERGE_CELL_TAG, mergeCellTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_MERGE_CELL_TAG, mergeCellTagHandler);
        RowTagHandler rowTagHandler = new RowTagHandler();
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.ROW_TAG, rowTagHandler);
        XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_ROW_TAG, rowTagHandler);
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        XlsxTagHandler handler = XLSX_CELL_HANDLER_MAP.get(name);
        if (handler == null || !handler.support(xlsxReadContext)) {
            return;
        }
        xlsxReadContext.xlsxReadSheetHolder().getTagDeque().push(name);
        handler.startElement(xlsxReadContext, name, attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String currentTag = xlsxReadContext.xlsxReadSheetHolder().getTagDeque().peek();
        if (currentTag == null) {
            return;
        }
        XlsxTagHandler handler = XLSX_CELL_HANDLER_MAP.get(currentTag);
        if (handler == null || !handler.support(xlsxReadContext)) {
            return;
        }
        handler.characters(xlsxReadContext, ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        XlsxTagHandler handler = XLSX_CELL_HANDLER_MAP.get(name);
        if (handler == null || !handler.support(xlsxReadContext)) {
            return;
        }
        handler.endElement(xlsxReadContext, name);
        xlsxReadContext.xlsxReadSheetHolder().getTagDeque().pop();
    }

}
