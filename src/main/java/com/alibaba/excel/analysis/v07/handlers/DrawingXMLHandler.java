package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.read.listener.ImageDataReadListener;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Handle drawingX.xml file. The character 'X' represents a number. e.g. drawing1.xml
 */
public class DrawingXMLHandler extends DefaultHandler {
    private ImageDataReadListener imageDataListener;
    private static final Map<String, XlsxImageTagHandler> XLSX_IMAGE_HANDLER_MAP =
        new HashMap<String, XlsxImageTagHandler>(32);
    private LinkedList<String> tagDeque = new LinkedList<String>();
    private PackagePart packagePart;

    static {
        ImageAnchorFromTagHandler imageAnchorFromTagHandler = new ImageAnchorFromTagHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_ANCHOR_FROM, imageAnchorFromTagHandler);

        ImageAnchorToTagHandler imageAnchorToTagHandler = new ImageAnchorToTagHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_ANCHOR_TO, imageAnchorToTagHandler);

        ImageAnchorColTagHandler imageAnchorColTagHandler = new ImageAnchorColTagHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_ANCHOR_COL, imageAnchorColTagHandler);

        ImageAnchorColOffTagHandler imageAnchorColoffTagHandler = new ImageAnchorColOffTagHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_ANCHOR_COLOFF, imageAnchorColoffTagHandler);

        ImageAnchorRowTagHandler imageAnchorRowTagHandler = new ImageAnchorRowTagHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_ANCHOR_ROW, imageAnchorRowTagHandler);

        ImageAnchorRowOffHandler imageAnchorRowOffHandler = new ImageAnchorRowOffHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_ANCHOR_ROWOFF, imageAnchorRowOffHandler);

        ImageRefIdTagHandler imageRefIdTagHandler = new ImageRefIdTagHandler();
        XLSX_IMAGE_HANDLER_MAP.put(ExcelXmlConstants.IMAGE_REF_ID, imageRefIdTagHandler);

    }

    public DrawingXMLHandler(PackagePart packagePart, ImageDataReadListener imageDataListener) {
        this.packagePart = packagePart;
        this.imageDataListener = imageDataListener;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        XlsxImageTagHandler handler = XLSX_IMAGE_HANDLER_MAP.get(name);
        if (handler == null) {
            return;
        }
        handler.startElement(imageDataListener, packagePart, name, attributes);
        tagDeque.push(name);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String currentTag = tagDeque.peek();
        if (currentTag == null) {
            return;
        }
        XlsxImageTagHandler handler = XLSX_IMAGE_HANDLER_MAP.get(currentTag);
        if (handler == null) {
            return;
        }
        handler.characters(imageDataListener, ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        XlsxImageTagHandler handler = XLSX_IMAGE_HANDLER_MAP.get(name);
        if (handler == null) {
            return;
        }
        handler.endElement(imageDataListener, name);
        tagDeque.pop();
    }

}
