package com.alibaba.excel.analysis.v07;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.xmlbeans.XmlException;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.alibaba.excel.analysis.BaseSaxAnalyser;
import com.alibaba.excel.cache.Cache;
import com.alibaba.excel.cache.EhcacheFile;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;

/**
 *
 * @author jipengfei
 */
public class XlsxSaxAnalyser extends BaseSaxAnalyser {

    private XSSFReader xssfReader;

    private Cache cache;

    private List<SheetSource> sheetSourceList = new ArrayList<SheetSource>();

    private boolean use1904WindowDate = false;

    public XlsxSaxAnalyser(AnalysisContext analysisContext) throws IOException, OpenXML4JException, XmlException {
        this.analysisContext = analysisContext;

        analysisContext.setCurrentRowNum(0);
        // OPCPackage pkg =
        // OPCPackage.open(new File("D:\\git\\easyexcel\\src\\test\\resources\\read\\large\\large07.xlsx"));
//        InputStream input =
//            Thread.currentThread().getContextClassLoader().getResourceAsStream("read/large/large07.xlsx");
//        File ff = new File("D:\\git\\easyexcel\\src\\test\\resources\\read\\large\\large0722.xlsx");
//        OutputStream os = new FileOutputStream(ff);
//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        while ((bytesRead = input.read(buffer, 0, 8192)) != -1) {
//            os.write(buffer, 0, bytesRead);
//        }
//        os.close();
//        input.close();
//        OPCPackage pkg = OPCPackage.open(ff);

         OPCPackage pkg =
         OPCPackage.open(Thread.currentThread().getContextClassLoader().getResourceAsStream("read/large/large07.xlsx"));
        this.xssfReader = new XSSFReader(pkg);
        ArrayList<PackagePart> parts = pkg.getPartsByContentType(XSSFRelation.SHARED_STRINGS.getContentType());
        PackagePart packagePart = parts.get(0);
        // InputStream sheet = Thread.currentThread().getContextClassLoader()
        // .getResourceAsStream("read/large/large07/xl/sharedStrings.xml");
        InputStream sheet = packagePart.getInputStream();
        InputSource sheetSource1 = new InputSource(sheet);
        this.cache = new EhcacheFile();
        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            ContentHandler handler = new SharedStringsTableHandler(cache);
            xmlReader.setContentHandler(handler);
            xmlReader.parse(sheetSource1);
            sheet.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelAnalysisException(e);
        }
        cache.finish();
        // this.cache=new SharedStringsTableCache(xssfReader.getSharedStringsTable());
        // InputStream workbookXml = xssfReader.getWorkbookData();
        // WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
        // CTWorkbook wb = ctWorkbook.getWorkbook();
        // CTWorkbookPr prefix = wb.getWorkbookPr();
        // if (prefix != null) {
        // this.use1904WindowDate = prefix.getDate1904();
        // }
        this.analysisContext.setUse1904WindowDate(use1904WindowDate);

        // sheetSourceList.add(new SheetSource("test", Thread.currentThread().getContextClassLoader()
        // .getResourceAsStream("read/large/large07/xl/worksheets/sheet1.xml")));
        XSSFReader.SheetIterator ite;
        sheetSourceList = new ArrayList<SheetSource>();

        ite = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        while (ite.hasNext()) {
            InputStream inputStream = ite.next();
            String sheetName = ite.getSheetName();
            SheetSource sheetSource = new SheetSource(sheetName, inputStream);
            sheetSourceList.add(sheetSource);
        }

    }

    @Override
    protected void execute() {
        Sheet sheetParam = analysisContext.getCurrentSheet();
        if (sheetParam != null && sheetParam.getSheetNo() > 0 && sheetSourceList.size() >= sheetParam.getSheetNo()) {
            InputStream sheetInputStream = sheetSourceList.get(sheetParam.getSheetNo() - 1).getInputStream();
            parseXmlSource(sheetInputStream);

        } else {
            int i = 0;
            for (SheetSource sheetSource : sheetSourceList) {
                i++;
                analysisContext.setCurrentSheet(new Sheet(i, sheetParam.getReadHeadRowNumber()));
                parseXmlSource(sheetSource.getInputStream());
            }
        }
    }

    private void parseXmlSource(InputStream inputStream) {
        InputSource sheetSource = new InputSource(inputStream);
        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            ContentHandler handler = new XlsxRowHandler(this, cache, analysisContext);
            xmlReader.setContentHandler(handler);
            xmlReader.parse(sheetSource);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelAnalysisException(e);
        }
    }

    @Override
    public List<Sheet> getSheets() {
        List<Sheet> sheets = new ArrayList<Sheet>();
        int i = 1;
        for (SheetSource sheetSource : sheetSourceList) {
            Sheet sheet = new Sheet(i, 0);
            sheet.setSheetName(sheetSource.getSheetName());
            i++;
            sheets.add(sheet);
        }

        return sheets;
    }

    class SheetSource {

        private String sheetName;

        private InputStream inputStream;

        public SheetSource(String sheetName, InputStream inputStream) {
            this.sheetName = sheetName;
            this.inputStream = inputStream;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }
}
