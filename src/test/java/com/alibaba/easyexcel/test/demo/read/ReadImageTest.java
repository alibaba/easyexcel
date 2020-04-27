package com.alibaba.easyexcel.test.demo.read;

import com.alibaba.excel.analysis.v07.handlers.DrowingMLHandler;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.util.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.internal.ContentType;
import org.apache.poi.openxml4j.opc.internal.ContentTypeManager;
import org.apache.poi.openxml4j.opc.internal.ZipContentTypeManager;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;


public class ReadImageTest {
    @Test
    public void parseXmlTest() throws OpenXML4JException, IOException, SAXException, ParserConfigurationException {
        OPCPackage pkg = OPCPackage.open("C:\\Users\\赵朋亮\\Desktop\\pictureTest.xlsx", PackageAccess.READ);
       // ContentTypeManager contentTypeManager= pkg.getPartsByContentType();
       // ArrayList<PackagePart> getPartsByContentType(String contentType)
        ArrayList<PackagePart> packagePartList=pkg.getParts();
        //遍历packagePartList，获得属性为image的PackagePart
        ArrayList<PackagePart> imagePackagePartList = new ArrayList<PackagePart>();
        for (PackagePart packagePart : packagePartList) {
            String contentType=packagePart.getContentType();
//            if(contentType.startsWith("image")) {
//                imagePackagePartList.add(packagePart);
//                continue;
//            }
            if(contentType.equals("application/vnd.openxmlformats-officedocument.drawing+xml")) {
                imagePackagePartList.add(packagePart);
            }
        }
        //解析主文件生成数据
        for (PackagePart packagePart : imagePackagePartList) {
          //  packagePart.getRelationship();
            InputStream in=packagePart.getInputStream();
            InputSource inputSource = new InputSource(packagePart.getInputStream());
//
            SAXParserFactory saxFactory;
            saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new DrowingMLHandler());
            xmlReader.parse(inputSource);
            in.close();

//            try {
//                XSSFReader reader = new XSSFReader(pkg);
//                SharedStringsTable sst = reader.getSharedStringsTable();
//                StylesTable st = reader.getStylesTable();
//                XMLReader parser = XMLReaderFactory.createXMLReader();
//                // 处理公共属性：Sheet名，Sheet合并单元格
//                parser.setContentHandler(new SheetHandler());
//                /**
//                 * 返回一个迭代器，此迭代器会依次得到所有不同的sheet。
//                 * 每个sheet的InputStream只有从Iterator获取时才会打开。
//                 * 解析完每个sheet时关闭InputStream。
//                 * */
//                XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) reader.getSheetsData();
//                while (sheets.hasNext()) {
//                    InputStream sheetstream = sheets.next();
//                    InputSource sheetSource = new InputSource(sheetstream);
//                    try {
//                        // 解析sheet: com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl:522
//                        parser.parse(sheetSource);
//                    } finally {
//                        sheetstream.close();
//                    }
//                }
//            } finally {
//                pkg.close();
//            }


            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//创建一个Buffer字符串
            byte[] buffer = new byte[1024];
//每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
//使用一个输入流从buffer里把数据读取出来
            while( (len=in.read(buffer)) != -1 ){
//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
//关闭输入流
          //  inStream.close();
//把outStream里的数据写入内存

//得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = outStream.toByteArray();
//new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File("C:\\Users\\赵朋亮\\Desktop\\pictureTest.bmp");
//创建输出流
            FileOutputStream fileOutStream = new FileOutputStream(imageFile);
//写入数据
            fileOutStream .write(data);

            String packagePartString=packagePart.toString();
            PackagePartName packagePartName=packagePart.getPartName();
            URI uri=packagePartName.getURI();
            int n=5;
        }

        int n=0;
      //  ContentTypeManager contentTypeManager= new ZipContentTypeManager(pkg);
        XSSFReader r = new XSSFReader( pkg );
      //  InputSource inputSource = new InputSource(inputStream);
//        try {
//            SAXParserFactory saxFactory;
//            String xlsxSAXParserFactoryName = xlsxReadContext.xlsxReadWorkbookHolder().getSaxParserFactoryName();
//            if (StringUtils.isEmpty(xlsxSAXParserFactoryName)) {
//                saxFactory = SAXParserFactory.newInstance();
//            } else {
//                saxFactory = SAXParserFactory.newInstance(xlsxSAXParserFactoryName, null);
//            }
//            saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
//            saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
//            saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//            SAXParser saxParser = saxFactory.newSAXParser();
//            XMLReader xmlReader = saxParser.getXMLReader();
//           xmlReader.setContentHandler(handler);
//            xmlReader.parse(inputSource);
//            inputStream.close();
//        } catch (ExcelAnalysisException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ExcelAnalysisException(e);
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    throw new ExcelAnalysisException("Can not close 'inputStream'!");
//                }
//            }
//        }
    }
}
