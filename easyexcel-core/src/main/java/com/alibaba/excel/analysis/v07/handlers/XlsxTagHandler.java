package com.alibaba.excel.analysis.v07.handlers;

import org.xml.sax.Attributes;

import com.alibaba.excel.context.xlsx.XlsxReadContext;

/**
 * Tag handler
 *
 * @author Dan Zheng
 */
public interface XlsxTagHandler {

    /**
     * Whether to support
     *
     * @param xlsxReadContext
     * @return
     */
    boolean support(XlsxReadContext xlsxReadContext);

    /**
     * Start handle
     *
     * @param xlsxReadContext
     *            xlsxReadContext
     * @param name
     *            Tag name
     * @param attributes
     *            Tag attributes
     */
    void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes);

    /**
     * End handle
     *
     * @param xlsxReadContext
     *            xlsxReadContext
     * @param name
     *            Tag name
     */
    void endElement(XlsxReadContext xlsxReadContext, String name);

    /**
     * Read data
     *
     * @param xlsxReadContext
     * @param ch
     * @param start
     * @param length
     */
    void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length);

}
