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
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.util.FileUtils;

/**
 *
 * @author jipengfei
 */
public class XlsxSaxAnalyser implements ExcelExecutor {
    private AnalysisContext analysisContext;
    private List<ReadSheet> sheetList;
    private Map<Integer, InputStream> sheetMap;

    public XlsxSaxAnalyser(AnalysisContext analysisContext) throws Exception {
        this.analysisContext = analysisContext;
        // Initialize cache
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();
        if (readWorkbookHolder.getReadCache() == null) {
            readWorkbookHolder.setReadCache(new Ehcache());
        }
        readWorkbookHolder.getReadCache().init(analysisContext);

        OPCPackage pkg = readOpcPackage(readWorkbookHolder);

        // Analysis sharedStringsTable.xml
        analysisSharedStringsTable(pkg, readWorkbookHolder);

        XSSFReader xssfReader = new XSSFReader(pkg);

        analysisUse1904WindowDate(xssfReader, readWorkbookHolder);

        sheetList = new ArrayList<ReadSheet>();
        sheetMap = new HashMap<Integer, InputStream>();
        XSSFReader.SheetIterator ite = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        int index = 0;
        if (!ite.hasNext()) {
            throw new ExcelAnalysisException("Can not find any sheet!");
        }
        while (ite.hasNext()) {
            InputStream inputStream = ite.next();
            sheetList.add(new ReadSheet(index, ite.getSheetName()));
            sheetMap.put(index, inputStream);
            index++;
        }
    }

    private void analysisUse1904WindowDate(XSSFReader xssfReader, ReadWorkbookHolder readWorkbookHolder)
        throws Exception {
        if (readWorkbookHolder.globalConfiguration().getUse1904windowing() != null) {
            return;
        }
        InputStream workbookXml = xssfReader.getWorkbookData();
        WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
        CTWorkbook wb = ctWorkbook.getWorkbook();
        CTWorkbookPr prefix = wb.getWorkbookPr();
        if (prefix != null && prefix.getDate1904()) {
            readWorkbookHolder.getGlobalConfiguration().setUse1904windowing(Boolean.TRUE);
        }
    }

    private void analysisSharedStringsTable(OPCPackage pkg, ReadWorkbookHolder readWorkbookHolder) throws Exception {
        ContentHandler handler = new SharedStringsTableHandler(readWorkbookHolder.getReadCache());
        parseXmlSource(pkg.getPartsByContentType(XSSFRelation.SHARED_STRINGS.getContentType()).get(0).getInputStream(),
            handler);
        readWorkbookHolder.getReadCache().putFinished();
    }

    private OPCPackage readOpcPackage(ReadWorkbookHolder readWorkbookHolder) throws Exception {
        if (readWorkbookHolder.getFile() != null) {
            return OPCPackage.open(readWorkbookHolder.getFile());
        }
        if (readWorkbookHolder.getMandatoryUseInputStream()) {
            return OPCPackage.open(readWorkbookHolder.getInputStream());
        }
        File readTempFile = FileUtils.createCacheTmpFile();
        readWorkbookHolder.setTempFile(readTempFile);
        File tempFile = new File(readTempFile.getPath(), UUID.randomUUID().toString() + ".xlsx");
        FileUtils.writeToFile(readTempFile, readWorkbookHolder.getInputStream());
        return OPCPackage.open(tempFile);
    }

    @Override
    public List<ReadSheet> sheetList() {
        return sheetList;
    }

    @Override
    public void execute() {
        parseXmlSource(sheetMap.get(analysisContext.readSheetHolder().getSheetNo()),
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
