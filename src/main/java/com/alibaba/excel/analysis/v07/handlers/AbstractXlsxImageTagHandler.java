package com.alibaba.excel.analysis.v07.handlers;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.xml.sax.Attributes;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Abstract class for handling image related tags in a XML file
 *
 * @author Pengliang Zhao
 */
public abstract class AbstractXlsxImageTagHandler implements XlsxImageTagHandler {
    @Override
    public void startElement(ImageDataReadListener imageDataListener, PackagePart packagePart, String name,
        Attributes attributes) {

    }

    @Override
    public void endElement(ImageDataReadListener imageDataListener, String name) {

    }

    @Override
    public void characters(ImageDataReadListener imageDataListener, char[] ch, int start, int length) {

    }
}
