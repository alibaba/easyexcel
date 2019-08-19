package com.alibaba.excel.analysis.v07;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.excel.cache.ReadCache;

/**
 * Sax read sharedStringsTable.xml
 *
 * @author Jiaju Zhuang
 */
public class SharedStringsTableHandler extends DefaultHandler {
    private static final String T_TAG = "t";
    private static final String SI_TAG = "si";
    /**
     * The final piece of data
     */
    private String currentData;
    /**
     * Current element data
     */
    private String currentElementData;

    private ReadCache readCache;

    public SharedStringsTableHandler(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        if (SI_TAG.equals(name)) {
            currentData = "";
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) {
        if (T_TAG.equals(name)) {
            currentData += currentElementData;
        } else if (SI_TAG.equals(name)) {
            readCache.put(currentData);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        currentElementData = new String(ch, start, length);
    }
}
