package com.alibaba.excel.analysis.v07.handlers;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.xml.sax.Attributes;

import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handler of image related tags in XML file
 *
 * @author Pengliang Zhao
 */
public interface XlsxImageTagHandler {

    /**
     * Receive notification of the start of an element
     *
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     * @param packagePart
     *            Parent packagePart
     * @param name
     *            Tag name
     * @param attributes
     *            Tag attributes
     */
    void startElement(ImageDataReadListener imageDataReadListener, PackagePart packagePart, String name,
        Attributes attributes);

    /**
     * Receive notification of the end of an element
     *
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     * @param name
     *            Tag name
     */
    void endElement(ImageDataReadListener imageDataReadListener, String name);

    /**
     * Receive notification of character data inside an element
     *
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     * @param ch
     * @param start
     * @param length
     */
    void characters(ImageDataReadListener imageDataReadListener, char[] ch, int start, int length);
}
