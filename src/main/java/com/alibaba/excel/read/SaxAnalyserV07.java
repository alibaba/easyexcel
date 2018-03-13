package com.alibaba.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import com.alibaba.excel.read.v07.RowHandler;
import com.alibaba.excel.read.v07.XmlParserFactory;
import com.alibaba.excel.read.v07.XMLTempFile;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.util.FileUtil;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbookPr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author jipengfei
 */
public class SaxAnalyserV07 extends BaseSaxAnalyser {

    private SharedStringsTable sharedStringsTable;

    private List<String> sharedStringList = new LinkedList<String>();

    private List<SheetSource> sheetSourceList = new ArrayList<SheetSource>();

    private boolean use1904WindowDate = false;

    private final String path;

    private File tmpFile;

    private String workBookXMLFilePath;

    private String sharedStringXMLFilePath;

    public SaxAnalyserV07(AnalysisContext analysisContext) throws Exception {
        this.analysisContext = analysisContext;
        this.path = XMLTempFile.createPath();
        this.tmpFile = new File(XMLTempFile.getTmpFilePath(path));
        this.workBookXMLFilePath = XMLTempFile.getWorkBookFilePath(path);
        this.sharedStringXMLFilePath = XMLTempFile.getSharedStringFilePath(path);
        start();
    }

    @Override
    protected void execute() {
        try {
            Sheet sheet = analysisContext.getCurrentSheet();
            if (!isAnalysisAllSheets(sheet)) {
                if (this.sheetSourceList.size() < sheet.getSheetNo() || sheet.getSheetNo() == 0) {
                    return;
                }
                InputStream sheetInputStream = this.sheetSourceList.get(sheet.getSheetNo() - 1).getInputStream();
                parseXmlSource(sheetInputStream);
                return;
            }
            int i = 0;
            for (SheetSource sheetSource : this.sheetSourceList) {
                i++;
                this.analysisContext.setCurrentSheet(new Sheet(i));
                parseXmlSource(sheetSource.getInputStream());
            }

        } catch (Exception e) {
            stop();
            throw new ExcelAnalysisException(e);
        } finally {
        }

    }

    private boolean isAnalysisAllSheets(Sheet sheet) {
        if (sheet == null) {
            return true;
        }
        if (sheet.getSheetNo() < 0) {
            return true;
        }
        return false;
    }

    public void stop() {
        FileUtil.deletefile(path);
    }

    private void parseXmlSource(InputStream inputStream) {
        try {
            ContentHandler handler = new RowHandler(this, this.sharedStringsTable, this.analysisContext,
                sharedStringList);
            XmlParserFactory.parse(inputStream, handler);
            inputStream.close();
        } catch (Exception e) {
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            throw new ExcelAnalysisException(e);
        }
    }

    public List<Sheet> getSheets() {
        List<Sheet> sheets = new ArrayList<Sheet>();
        try {
            int i = 1;
            for (SheetSource sheetSource : this.sheetSourceList) {
                Sheet sheet = new Sheet(i, 0);
                sheet.setSheetName(sheetSource.getSheetName());
                i++;
                sheets.add(sheet);
            }
        } catch (Exception e) {
            stop();
            throw new ExcelAnalysisException(e);
        } finally {

        }

        return sheets;
    }

    private void start() throws IOException, XmlException, ParserConfigurationException, SAXException {

        createTmpFile();

        unZipTempFile();

        initSharedStringsTable();

        initUse1904WindowDate();

        initSheetSourceList();

    }

    private void createTmpFile() throws FileNotFoundException {
        FileUtil.writeFile(tmpFile, analysisContext.getInputStream());
    }

    private void unZipTempFile() throws IOException {
        FileUtil.doUnZip(path, tmpFile);
    }

    private void initSheetSourceList() throws IOException, ParserConfigurationException, SAXException {
        this.sheetSourceList = new ArrayList<SheetSource>();
        InputStream workbookXml = new FileInputStream(this.workBookXMLFilePath);
        XmlParserFactory.parse(workbookXml, new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
                if (qName.toLowerCase(Locale.US).equals("sheet")) {
                    String name = null;
                    int id = 0;
                    for (int i = 0; i < attrs.getLength(); i++) {
                        if (attrs.getLocalName(i).toLowerCase(Locale.US).equals("name")) {
                            name = attrs.getValue(i);
                        } else if (attrs.getLocalName(i).toLowerCase(Locale.US).equals("sheetid")) {
                            //这里应该是获取sheetId而不是r:id属性。否则，下面拼接的sheet+id的xml找不到。
                            id = Integer.parseInt(attrs.getValue(i));
                            try {
                                InputStream inputStream = new FileInputStream(XMLTempFile.getSheetFilePath(path, id));
                                sheetSourceList.add(new SheetSource(id, name, inputStream));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }

        });
        workbookXml.close();
        /*这种排序方法底层实现是使用数组元素的排序方式，
        进而调用java.util.ComparableTimSort.sort(Object[] a,int lo,int hi,@Nullable Object[] work,int workBase,int workLen)
        再调用内部方法countRunAndMakeAscending(Object[] a, int lo, int hi),
        问题就出现在这个私有方法，它是用后面的元素跟前面的元素进行对比，会导致倒序排列元素。本来是期望sheetList按照sheet号正序排列，
        然后取第二个sheet，但是倒序后，变成取了第一个sheet。*/
        //Collections.sort(sheetSourceList);
        Collections.sort(sheetSourceList, new Comparator<SheetSource>() {
            @Override
            public int compare(SheetSource o1, SheetSource o2) {
                if (o1.id == o2.id) {
                    return 0;
                } else if (o1.id > o2.id) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    private void initUse1904WindowDate() throws IOException, XmlException {
        InputStream workbookXml = new FileInputStream(workBookXMLFilePath);
        WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
        CTWorkbook wb = ctWorkbook.getWorkbook();
        CTWorkbookPr prefix = wb.getWorkbookPr();
        if (prefix != null) {
            this.use1904WindowDate = prefix.getDate1904();
        }
        this.analysisContext.setUse1904WindowDate(use1904WindowDate);
        workbookXml.close();
    }

    private void initSharedStringsTable() throws IOException, ParserConfigurationException, SAXException {

        InputStream inputStream = new FileInputStream(this.sharedStringXMLFilePath);
        //this.sharedStringsTable = new SharedStringsTable();
        //this.sharedStringsTable.readFrom(inputStream);

        XmlParserFactory.parse(inputStream, new DefaultHandler() {
            @Override
            public void characters(char[] ch, int start, int length) {
                sharedStringList.add(new String(ch, start, length));
            }

        });
        inputStream.close();
    }

    private class SheetSource implements Comparable<SheetSource> {

        private int id;

        private String sheetName;

        private InputStream inputStream;

        public SheetSource(int id, String sheetName, InputStream inputStream) {
            this.id = id;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int compareTo(SheetSource o) {
            if (o.id == this.id) {
                return 0;
            } else if (o.id > this.id) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
