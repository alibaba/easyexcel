package com.alibaba.excel.analysis.v07.handlers;

import org.xml.sax.Attributes;

import com.alibaba.excel.context.xlsx.XlsxReadContext;

/**
 * Cell handler
 *
 * @author Dan Zheng
 */
public interface XlsxCellHandler {
    /**
     * Start handle
     *
     * @param xlsxReadContext xlsxReadContext
     * @param name            Tag name
     * @param attributes      Tag attributes
     */
    void startHandle(XlsxReadContext xlsxReadContext, String name, Attributes attributes);

    /**
     * End handle
     *
     * @param xlsxReadContext xlsxReadContext
     * @param name            Tag name
     */
    void endHandle(XlsxReadContext xlsxReadContext, String name);
}
