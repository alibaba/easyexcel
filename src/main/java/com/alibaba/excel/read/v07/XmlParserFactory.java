package com.alibaba.excel.read.v07;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author jipengfei
 *
 */
public class XmlParserFactory {

    public static void parse(InputStream inputStream, ContentHandler contentHandler)
        throws ParserConfigurationException, SAXException, IOException {
        InputSource sheetSource = new InputSource(inputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(contentHandler);
        xmlReader.parse(sheetSource);
    }
}
