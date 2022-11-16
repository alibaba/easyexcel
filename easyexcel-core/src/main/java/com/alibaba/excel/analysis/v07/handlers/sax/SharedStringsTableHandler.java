package com.alibaba.excel.analysis.v07.handlers.sax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.constant.ExcelXmlConstants;

/**
 * Sax read sharedStringsTable.xml
 *
 * @author Jiaju Zhuang
 */
public class SharedStringsTableHandler extends DefaultHandler {

    /**
     * The final piece of data
     */
    private StringBuilder currentData;
    /**
     * Current element data
     */
    private StringBuilder currentElementData;

    private final ReadCache readCache;
    /**
     * Some fields in the T tag need to be ignored
     */
    private boolean ignoreTagt = false;
    /**
     * The only time you need to read the characters in the T tag is when it is used
     */
    private boolean isTagt = false;

    public SharedStringsTableHandler(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        if (name == null) {
            return;
        }
        switch (name) {
            case ExcelXmlConstants.SHAREDSTRINGS_T_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_T_TAG:
                currentElementData = null;
                isTagt = true;
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_SI_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_SI_TAG:
                currentData = null;
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_RPH_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_RPH_TAG:
                ignoreTagt = true;
                break;
            default:
                // ignore
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) {
        if (name == null) {
            return;
        }
        switch (name) {
            case ExcelXmlConstants.SHAREDSTRINGS_T_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_T_TAG:
                if (currentElementData != null) {
                    if (currentData == null) {
                        currentData = new StringBuilder();
                    }
                    currentData.append(currentElementData);
                }
                isTagt = false;
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_SI_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_SI_TAG:
                if (currentData == null) {
                    readCache.put(null);
                } else {
                    readCache.put(currentData.toString());
                }
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_RPH_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_RPH_TAG:
                ignoreTagt = false;
                break;
            default:
                // ignore
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (!isTagt || ignoreTagt) {
            return;
        }
        if (currentElementData == null) {
            currentElementData = new StringBuilder();
        }
        currentElementData.append(ch, start, length);
    }
}
