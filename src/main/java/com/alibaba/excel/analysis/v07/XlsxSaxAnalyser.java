package com.alibaba.excel.analysis.v07;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbookPr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.alibaba.excel.analysis.ExcelExecutor;
import com.alibaba.excel.cache.Ehcache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.write.metadata.Sheet;
import com.alibaba.excel.write.metadata.holder.WorkbookHolder;
import com.alibaba.excel.util.FileUtils;

/**
 *
 * @author jipengfei
 */
public class XlsxSaxAnalyser implements ExcelExecutor {
    private AnalysisContext analysisContext;
    private List<Sheet> sheetList;
    private Map<Integer, InputStream> sheetMap;

    public XlsxSaxAnalyser(AnalysisContext analysisContext) throws Exception {
        this.analysisContext = analysisContext;
        analysisContext.setCurrentRowNum(0);
        WorkbookHolder workbookHolder = analysisContext.currentWorkbookHolder();
        if (workbookHolder.getReadCache() == null) {
            workbookHolder.setReadCache(new Ehcache());
        }
        workbookHolder.getReadCache().init(analysisContext);

        OPCPackage pkg = readOpcPackage(workbookHolder);

        // Analysis sharedStringsTable.xml
        analysisSharedStringsTable(pkg, workbookHolder);

        XSSFReader xssfReader = new XSSFReader(pkg);

        analysisUse1904WindowDate(xssfReader, workbookHolder);

        sheetList = new ArrayList<Sheet>();
        sheetMap = new HashMap<Integer, InputStream>();
        XSSFReader.SheetIterator ite = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        int index = 0;
        while (ite.hasNext()) {
            InputStream inputStream = ite.next();
            Sheet sheet = new Sheet();
            sheet.setSheetNo(index);
            sheet.setSheetName(ite.getSheetName());
            sheetList.add(sheet);
            sheetMap.put(index, inputStream);
            index++;
        }

    }

    private void analysisUse1904WindowDate(XSSFReader xssfReader, WorkbookHolder workbookHolder) throws Exception {
        InputStream workbookXml = xssfReader.getWorkbookData();
        WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
        CTWorkbook wb = ctWorkbook.getWorkbook();
        CTWorkbookPr prefix = wb.getWorkbookPr();
        if (prefix != null && prefix.getDate1904()) {
            workbookHolder.setUse1904windowing(Boolean.TRUE);
        }
    }

    private void analysisSharedStringsTable(OPCPackage pkg, WorkbookHolder workbookHolder) throws Exception {
        ContentHandler handler = new SharedStringsTableHandler(workbookHolder.getReadCache());
        parseXmlSource(pkg.getPartsByContentType(XSSFRelation.SHARED_STRINGS.getContentType()).get(0).getInputStream(),
            handler);
        workbookHolder.getReadCache().putFinished();
    }

    private OPCPackage readOpcPackage(WorkbookHolder workbookHolder) throws Exception {
        if (workbookHolder.getFile() != null) {
            return OPCPackage.open(workbookHolder.getFile());
        }
        if (workbookHolder.getMandatoryUseInputStream()) {
            return OPCPackage.open(workbookHolder.getInputStream());
        }
        File readTempFile = FileUtils.createCacheTmpFile();
        workbookHolder.setReadTempFile(readTempFile);
        File tempFile = new File(readTempFile.getPath(), UUID.randomUUID().toString() + ".xlsx");
        FileUtils.writeToFile(readTempFile, workbookHolder.getInputStream());
        return OPCPackage.open(tempFile);
    }

    @Override
    public List<Sheet> sheetList() {
        return sheetList;
    }

    @Override
    public void execute() {
        parseXmlSource(sheetMap.get(analysisContext.currentSheetHolder().getSheetNo()),
            new XlsxRowHandler(analysisContext));
    }

    private void parseXmlSource(InputStream inputStream, ContentHandler handler) {
        InputSource inputSource = new InputSource(inputStream);
        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            inputStream.close();
        } catch (Exception e) {
            throw new ExcelAnalysisException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ExcelAnalysisException("Can not close 'inputStream'!");
                }
            }
        }
    }

}
