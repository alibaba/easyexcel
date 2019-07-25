package com.alibaba.excel.analysis.v07;

import org.xml.sax.Attributes;

/**
 * Cell handler
 *
 * @author Dan Zheng
 */
public interface XlsxCellHandler {
    /**
     * Which tags are supported
     * 
     * @param name
     * @return
     */
    boolean support(String name);

    /**
     * Start handle
     * 
     * @param name
     * @param attributes
     */
    void startHandle(String name, Attributes attributes);

    /**
     * End handle
     * 
     * @param name
     */
    void endHandle(String name);
}
