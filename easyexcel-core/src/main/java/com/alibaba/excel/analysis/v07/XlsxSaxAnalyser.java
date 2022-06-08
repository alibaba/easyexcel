package com.alibaba.excel.analysis.v07;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.analysis.v07.handlers.sax.SharedStringsTableHandler;
import com.alibaba.excel.analysis.v07.handlers.sax.XlsxRowHandler;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.util.SheetUtils;
import com.alibaba.excel.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.CommentsTable;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbookPr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author jipengfei
 */
@Slf4j
public class XlsxSaxAnalyser implements ExcelReadExecutor {

    /**
     * Storage sheet SharedStrings
     */
    public static final PackagePartName SHARED_STRINGS_PART_NAME;

    static {
        try {
            SHARED_STRINGS_PART_NAME = PackagingURIHelper.createPartName("/xl/sharedStrings.xml");
        } catch (InvalidFormatException e) {
            log.error("Initialize the XlsxSaxAnalyser failure", e);
            throw new ExcelAnalysisException("Initialize the XlsxSaxAnalyser failure", e);
        }
    }

    private final XlsxReadContext xlsxReadContext;
    private final List<ReadSheet> sheetList;
    private final Map<Integer, InputStream> sheetMap;
    /**
     * excel comments key: sheetNo value: CommentsTable
     */
    private final Map<Integer, CommentsTable> commentsTableMap;

    public XlsxSaxAnalyser(XlsxReadContext xlsxReadContext, InputStream decryptedStream) throws Exception {
        this.xlsxReadContext = xlsxReadContext;
        // Initialize cache
        XlsxReadWorkbookHolder xlsxReadWorkbookHolder = xlsxReadContext.xlsxReadWorkbookHolder();

        OPCPackage pkg = readOpcPackage(xlsxReadWorkbookHolder, decryptedStream);
        xlsxReadWorkbookHolder.setOpcPackage(pkg);

        // Read the Shared information Strings
        PackagePart sharedStringsTablePackagePart = pkg.getPart(SHARED_STRINGS_PART_NAME);
        if (sharedStringsTablePackagePart != null) {
            // Specify default cache
            defaultReadCache(xlsxReadWorkbookHolder, sharedStringsTablePackagePart);

            // Analysis sharedStringsTable.xml
            analysisSharedStringsTable(sharedStringsTablePackagePart.getInputStream(), xlsxReadWorkbookHolder);
        }

        XSSFReader xssfReader = new XSSFReader(pkg);
        analysisUse1904WindowDate(xssfReader, xlsxReadWorkbookHolder);

        // set style table
        setStylesTable(xlsxReadWorkbookHolder, xssfReader);

        sheetList = new ArrayList<>();
        sheetMap = new HashMap<>();
        commentsTableMap = new HashMap<>();
        Map<Integer, PackageRelationshipCollection> packageRelationshipCollectionMap = MapUtils.newHashMap();
        xlsxReadWorkbookHolder.setPackageRelationshipCollectionMap(packageRelationshipCollectionMap);

        XSSFReader.SheetIterator ite = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        int index = 0;
        if (!ite.hasNext()) {
            throw new ExcelAnalysisException("Can not find any sheet!");
        }
        while (ite.hasNext()) {
            InputStream inputStream = ite.next();
            sheetList.add(new ReadSheet(index, ite.getSheetName()));
            sheetMap.put(index, inputStream);
            if (xlsxReadContext.readWorkbookHolder().getExtraReadSet().contains(CellExtraTypeEnum.COMMENT)) {
                CommentsTable commentsTable = ite.getSheetComments();
                if (null != commentsTable) {
                    commentsTableMap.put(index, commentsTable);
                }
            }
            if (xlsxReadContext.readWorkbookHolder().getExtraReadSet().contains(CellExtraTypeEnum.HYPERLINK)) {
                PackageRelationshipCollection packageRelationshipCollection = Optional.ofNullable(ite.getSheetPart())
                    .map(packagePart -> {
                        try {
                            return packagePart.getRelationships();
                        } catch (InvalidFormatException e) {
                            log.warn("Reading the Relationship failed", e);
                            return null;
                        }
                    }).orElse(null);
                if (packageRelationshipCollection != null) {
                    packageRelationshipCollectionMap.put(index, packageRelationshipCollection);
                }
            }
            index++;
        }
    }

    private void setStylesTable(XlsxReadWorkbookHolder xlsxReadWorkbookHolder, XSSFReader xssfReader) {
        try {
            xlsxReadWorkbookHolder.setStylesTable(xssfReader.getStylesTable());
        } catch (Exception e) {
            log.warn(
                "Currently excel cannot get style information, but it doesn't affect the data analysis.You can try to"
                    + " save the file with office again or ignore the current error.",
                e);
        }
    }

    private void defaultReadCache(XlsxReadWorkbookHolder xlsxReadWorkbookHolder,
        PackagePart sharedStringsTablePackagePart) {
        ReadCache readCache = xlsxReadWorkbookHolder.getReadCacheSelector().readCache(sharedStringsTablePackagePart);
        xlsxReadWorkbookHolder.setReadCache(readCache);
        readCache.init(xlsxReadContext);
    }

    private void analysisUse1904WindowDate(XSSFReader xssfReader, XlsxReadWorkbookHolder xlsxReadWorkbookHolder)
        throws Exception {
        if (xlsxReadWorkbookHolder.globalConfiguration().getUse1904windowing() != null) {
            return;
        }
        InputStream workbookXml = xssfReader.getWorkbookData();
        WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
        CTWorkbook wb = ctWorkbook.getWorkbook();
        CTWorkbookPr prefix = wb.getWorkbookPr();
        if (prefix != null && prefix.getDate1904()) {
            xlsxReadWorkbookHolder.getGlobalConfiguration().setUse1904windowing(Boolean.TRUE);
        } else {
            xlsxReadWorkbookHolder.getGlobalConfiguration().setUse1904windowing(Boolean.FALSE);
        }
    }

    private void analysisSharedStringsTable(InputStream sharedStringsTableInputStream,
        XlsxReadWorkbookHolder xlsxReadWorkbookHolder) throws Exception {
        ContentHandler handler = new SharedStringsTableHandler(xlsxReadWorkbookHolder.getReadCache());
        parseXmlSource(sharedStringsTableInputStream, handler);
        xlsxReadWorkbookHolder.getReadCache().putFinished();
    }

    private OPCPackage readOpcPackage(XlsxReadWorkbookHolder xlsxReadWorkbookHolder, InputStream decryptedStream)
        throws Exception {
        if (decryptedStream == null && xlsxReadWorkbookHolder.getFile() != null) {
            return OPCPackage.open(xlsxReadWorkbookHolder.getFile());
        }
        if (xlsxReadWorkbookHolder.getMandatoryUseInputStream()) {
            if (decryptedStream != null) {
                return OPCPackage.open(decryptedStream);
            } else {
                return OPCPackage.open(xlsxReadWorkbookHolder.getInputStream());
            }
        }
        File readTempFile = FileUtils.createCacheTmpFile();
        xlsxReadWorkbookHolder.setTempFile(readTempFile);
        File tempFile = new File(readTempFile.getPath(), UUID.randomUUID().toString() + ".xlsx");
        if (decryptedStream != null) {
            FileUtils.writeToFile(tempFile, decryptedStream, false);
        } else {
            FileUtils.writeToFile(tempFile, xlsxReadWorkbookHolder.getInputStream(),
                xlsxReadWorkbookHolder.getAutoCloseStream());
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
            SAXParserFactory saxFactory;
            String xlsxSAXParserFactoryName = xlsxReadContext.xlsxReadWorkbookHolder().getSaxParserFactoryName();
            if (StringUtils.isEmpty(xlsxSAXParserFactoryName)) {
                saxFactory = SAXParserFactory.newInstance();
            } else {
                saxFactory = SAXParserFactory.newInstance(xlsxSAXParserFactoryName, null);
            }
            try {
                saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            } catch (Throwable ignore) {}
            try {
                saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            } catch (Throwable ignore) {}
            try {
                saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Throwable ignore) {}
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            inputStream.close();
        } catch (IOException | ParserConfigurationException | SAXException e) {
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
    public void execute() {
        for (ReadSheet readSheet : sheetList) {
            readSheet = SheetUtils.match(readSheet, xlsxReadContext);
            if (readSheet != null) {
                xlsxReadContext.currentSheet(readSheet);
                parseXmlSource(sheetMap.get(readSheet.getSheetNo()), new XlsxRowHandler(xlsxReadContext));
                // Read comments
                readComments(readSheet);
                // The last sheet is read
                xlsxReadContext.analysisEventProcessor().endSheet(xlsxReadContext);
            }
        }
    }

    private void readComments(ReadSheet readSheet) {
        if (!xlsxReadContext.readWorkbookHolder().getExtraReadSet().contains(CellExtraTypeEnum.COMMENT)) {
            return;
        }
        CommentsTable commentsTable = commentsTableMap.get(readSheet.getSheetNo());
        if (commentsTable == null) {
            return;
        }
        Iterator<CellAddress> cellAddresses = commentsTable.getCellAddresses();
        while (cellAddresses.hasNext()) {
            CellAddress cellAddress = cellAddresses.next();
            XSSFComment cellComment = commentsTable.findCellComment(cellAddress);
            CellExtra cellExtra = new CellExtra(CellExtraTypeEnum.COMMENT, cellComment.getString().toString(),
                cellAddress.getRow(), cellAddress.getColumn());
            xlsxReadContext.readSheetHolder().setCellExtra(cellExtra);
            xlsxReadContext.analysisEventProcessor().extra(xlsxReadContext);
        }
    }
}
