package com.alibaba.excel.analysis.v07.handlers;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.alibaba.excel.constant.ExcelXmlConstants;

/**
 * Handler of workbook XML file
 */
public class WorkBookXMLHandler extends DefaultHandler {

    /**
     * Keeps worksheet name and it's relationship ID with workbook.
     */
    private Map<String, String> sheetNameMap = new HashMap<String, String>(32);

    /**
     * Keeps worksheet index and it's relationship ID with workbook. The index is 1-based.
     */
    private Map<Integer, String> sheetNoMap = new HashMap<Integer, String>(32);

    private PackagePart workBookPkg;

    public Map<String, String> getSheetNameMap() {
        return sheetNameMap;
    }

    public Map<Integer, String> getSheetNoMap() {
        return sheetNoMap;
    }

    public PackagePart getWorkBookPkg() {
        return workBookPkg;
    }

    public void setWorkBookPkg(PackagePart workBookPkg) {
        this.workBookPkg = workBookPkg;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if (!name.equals(ExcelXmlConstants.SHEET_TAG)) {
            return;
        }

        String rId = attributes.getValue("r:id");
        String sheetId = attributes.getValue("sheetId");
        String sheetName = attributes.getValue("name");
        sheetNameMap.put(sheetName, rId);
        sheetNoMap.put(Integer.valueOf(sheetId), rId);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {

    }
}
