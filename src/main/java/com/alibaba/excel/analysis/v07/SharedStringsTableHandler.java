package com.alibaba.excel.analysis.v07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.excel.cache.ReadCache;

/**
 * Sax read sharedStringsTable.xml
 * 
 * @author zhuangjiaju
 */
public class SharedStringsTableHandler extends DefaultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedStringsTableHandler.class);

    private String currentData;
    private boolean isT;
    private int index = 0;
    private ReadCache readCache;

    public SharedStringsTableHandler(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if ("t".equals(name)) {
            currentData = null;
            isT = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        if ("t".equals(name)) {
            currentData = null;
            isT = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isT) {
            readCache.put(index++, new String(ch, start, length));
            if (index % 100000 == 0) {
                LOGGER.info("row:{} ,mem:{},data:{}", index, Runtime.getRuntime().totalMemory());
            }
        }
    }
}
