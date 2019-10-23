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
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.CommentsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbookPr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.SheetUtils;
import com.alibaba.excel.util.StringUtils;

/**
 *
 * @author jipengfei
 */
public class XlsxSaxAnalyser implements ExcelReadExecutor {

    private AnalysisContext analysisContext;
    private List<ReadSheet> sheetList;
    private Map<Integer, InputStream> sheetMap;

    /**
     * excel comments
     * key: sheetNo
     * value: CommentsTanle
     */
    private Map<Integer, CommentsTable> commentsTableMap;

    /**
     * Current style information
     */
    private StylesTable stylesTable;

    public XlsxSaxAnalyser(AnalysisContext analysisContext, InputStream decryptedStream) throws Exception {
        this.analysisContext = analysisContext;
        // Initialize cache
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();

        OPCPackage pkg = readOpcPackage(readWorkbookHolder, decryptedStream);
        readWorkbookHolder.setOpcPackage(pkg);

        ArrayList<PackagePart> packageParts = pkg.getPartsByContentType(XSSFRelation.SHARED_STRINGS.getContentType());

        if (!CollectionUtils.isEmpty(packageParts)) {
            PackagePart sharedStringsTablePackagePart = packageParts.get(0);

            // Specify default cache
            defaultReadCache(readWorkbookHolder, sharedStringsTablePackagePart);

            // Analysis sharedStringsTable.xml
            analysisSharedStringsTable(sharedStringsTablePackagePart.getInputStream(), readWorkbookHolder);
        }

        XSSFReader xssfReader = new XSSFReader(pkg);
        analysisUse1904WindowDate(xssfReader, readWorkbookHolder);

        stylesTable = xssfReader.getStylesTable();
        sheetList = new ArrayList<ReadSheet>();
        sheetMap = new HashMap<Integer, InputStream>();
        commentsTableMap = new HashMap<Integer, CommentsTable>();
        XSSFReader.SheetIterator ite = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        int index = 0;
        if (!ite.hasNext()) {
            throw new ExcelAnalysisException("Can not find any sheet!");
        }
        while (ite.hasNext()) {
            InputStream inputStream = ite.next();
            sheetList.add(new ReadSheet(index, ite.getSheetName()));
            sheetMap.put(index, inputStream);
            CommentsTable commentsTable = ite.getSheetComments();
            if (null != commentsTable) {
                commentsTableMap.put(index, commentsTable);
            }
            index++;
        }
    }

    private void defaultReadCache(ReadWorkbookHolder readWorkbookHolder, PackagePart sharedStringsTablePackagePart) {
        ReadCache readCache = readWorkbookHolder.getReadCacheSelector().readCache(sharedStringsTablePackagePart);
        readWorkbookHolder.setReadCache(readCache);
        readCache.init(analysisContext);
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
        } else {
            readWorkbookHolder.getGlobalConfiguration().setUse1904windowing(Boolean.FALSE);
        }
    }

    private void analysisSharedStringsTable(InputStream sharedStringsTableInputStream,
        ReadWorkbookHolder readWorkbookHolder) throws Exception {
        ContentHandler handler = new SharedStringsTableHandler(readWorkbookHolder.getReadCache());
        parseXmlSource(sharedStringsTableInputStream, handler);
        readWorkbookHolder.getReadCache().putFinished();
    }

    private OPCPackage readOpcPackage(ReadWorkbookHolder readWorkbookHolder, InputStream decryptedStream)
        throws Exception {
        if (decryptedStream == null && readWorkbookHolder.getFile() != null) {
            return OPCPackage.open(readWorkbookHolder.getFile());
        }
        if (readWorkbookHolder.getMandatoryUseInputStream()) {
            if (decryptedStream != null) {
                return OPCPackage.open(decryptedStream);
            } else {
                return OPCPackage.open(readWorkbookHolder.getInputStream());
            }
        }
        File readTempFile = FileUtils.createCacheTmpFile();
        readWorkbookHolder.setTempFile(readTempFile);
        File tempFile = new File(readTempFile.getPath(), UUID.randomUUID().toString() + ".xlsx");
        if (decryptedStream != null) {
            FileUtils.writeToFile(tempFile, decryptedStream);
        } else {
            FileUtils.writeToFile(tempFile, readWorkbookHolder.getInputStream());
        }
        return OPCPackage.open(tempFile, PackageAccess.READ);
    }

    @Override
    public List<ReadSheet> sheetList() {
        return sheetList;
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
        } catch (ExcelAnalysisException e) {
            throw e;
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

    @Override
    public void execute(List<ReadSheet> readSheetList, Boolean readAll) {
        for (ReadSheet readSheet : sheetList) {
            readSheet = SheetUtils.match(readSheet, readSheetList, readAll,
                analysisContext.readWorkbookHolder().getGlobalConfiguration());
            if (readSheet != null) {
                analysisContext.currentSheet(readSheet);
                Integer sheetNo = readSheet.getSheetNo();
                parseXmlSource(sheetMap.get(sheetNo), new XlsxRowHandler(analysisContext, stylesTable, commentsTableMap.get(sheetNo)));
                // The last sheet is read
                analysisContext.readSheetHolder().notifyAfterAllAnalysed(analysisContext);
            }
        }
    }
}
