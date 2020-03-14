package com.alibaba.excel.analysis.v07.handlers.sax;

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
     * Mac 2016 2017 will have this extra field to ignore
     */
    private static final String RPH_TAG = "rPh";

    /**
     * The final piece of data
     */
    private StringBuilder currentData;
    /**
     * Current element data
     */
    private StringBuilder currentElementData;

    private ReadCache readCache;
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
        if (T_TAG.equals(name)) {
            currentElementData = null;
            isTagt = true;
        } else if (SI_TAG.equals(name)) {
            currentData = null;
        } else if (RPH_TAG.equals(name)) {
            ignoreTagt = true;
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
            isTagt = false;
        } else if (SI_TAG.equals(name)) {
            if (currentData == null) {
                readCache.put(null);
            } else {
                readCache.put(currentData.toString());
            }
        } else if (RPH_TAG.equals(name)) {
            ignoreTagt = false;
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
