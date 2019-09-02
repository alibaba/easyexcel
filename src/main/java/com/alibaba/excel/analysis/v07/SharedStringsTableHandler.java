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
    private StringBuilder currentData;
    /**
     * Current element data
     */
    private StringBuilder currentElementData;

    private ReadCache readCache;

    public SharedStringsTableHandler(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        if (T_TAG.equals(name)) {
            currentElementData = null;
        } else if (SI_TAG.equals(name)) {
            currentData = null;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) {
        if (T_TAG.equals(name)) {
            if (currentElementData != null) {
                if (currentData == null) {
                    currentData = new StringBuilder();
                }
                currentData.append(currentElementData);
            }
        } else if (SI_TAG.equals(name)) {
            if (currentData == null) {
                readCache.put(null);
            } else {
                readCache.put(currentData.toString());
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentElementData == null) {
            currentElementData = new StringBuilder();
        }
        currentElementData.append(new String(ch, start, length));
    }
}
