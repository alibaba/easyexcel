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

    /**
     * xml解析
     * @param inputStream
     * @param contentHandler
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void parse(InputStream inputStream, ContentHandler contentHandler)
        throws ParserConfigurationException, SAXException, IOException {
        InputSource sheetSource = new InputSource(inputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        //防止XML实体注注入
        saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(contentHandler);
        xmlReader.parse(sheetSource);
    }
}
