package com.alibaba.excel.analysis.v07;

import org.apache.poi.ss.usermodel.Comment;
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
     *            Tag name
     * @return Support parsing or not
     */
    boolean support(String name);

    /**
     * Start handle
     *
     * @param name
     *            Tag name
     * @param attributes
     *            Tag attributes
     */
    void startHandle(String name, Attributes attributes, Comment comment);

    /**
     * End handle
     *
     * @param name
     *            Tag name
     */
    void endHandle(String name);
}
