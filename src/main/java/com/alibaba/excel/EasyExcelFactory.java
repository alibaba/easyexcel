package com.alibaba.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.alibaba.excel.analysis.v07.handlers.DrawingXMLHandler;
import com.alibaba.excel.analysis.v07.handlers.WorkBookXMLHandler;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ImageDataReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;

/**
 * Reader and writer factory class
 *
 * <h1>Quick start</h1>
 * <h2>Read</h2>
 * <h3>Sample1</h3>
 *
 * <h3>Sample2</h3>
 *
 * <h2>Write</h2>
 *
 * <h3>Sample1</h3>
 *
 * <h3>Sample2</h3>
 *
 * @author jipengfei
 */
public class EasyExcelFactory {

    /**
     * Quickly read small files，no more than 10,000 lines.
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream.
     * @param sheet
     *            read sheet.
     * @return analysis result.
     * @deprecated please use 'EasyExcel.read(in).sheet(sheetNo).doReadSync();'
     */
    @Deprecated
    public static List<Object> read(InputStream in, Sheet sheet) {
        final List<Object> rows = new ArrayList<Object>();
        new ExcelReader(in, null, new AnalysisEventListener<Object>() {
            @Override
            public void invoke(Object object, AnalysisContext context) {
                rows.add(object);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {}
        }, false).read(sheet);
        return rows;
    }

    /**
     * Parsing large file
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream.
     * @param sheet
     *            read sheet.
     * @param listener
     *            Callback method after each row is parsed.
     * @deprecated please use 'EasyExcel.read(in,head,listener).sheet(sheetNo).doRead();'
     */
    @Deprecated
    public static void readBySax(InputStream in, Sheet sheet, AnalysisEventListener listener) {
        new ExcelReader(in, null, listener).read(sheet);
    }

    /**
     * Get ExcelReader.
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream.
     * @param listener
     *            Callback method after each row is parsed.
     * @return ExcelReader.
     * @deprecated please use {@link EasyExcel#read()} build 'ExcelReader'
     */
    @Deprecated
    public static ExcelReader getReader(InputStream in, AnalysisEventListener listener) {
        return new ExcelReader(in, null, listener);
    }

    /**
     * Get ExcelWriter
     *
     * @param outputStream
     *            the java OutputStream you wish to write the value to.
     * @return new ExcelWriter.
     * @deprecated please use {@link EasyExcel#write()}
     */
    @Deprecated
    public static ExcelWriter getWriter(OutputStream outputStream) {
        return write().file(outputStream).autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).build();
    }

    /**
     * Get ExcelWriter
     *
     * @param outputStream
     *            the java OutputStream you wish to write the value to.
     * @param typeEnum
     *            03 or 07
     * @param needHead
     *            Do you need to write the header to the file?
     * @return new ExcelWriter
     * @deprecated please use {@link EasyExcel#write()}
     */
    @Deprecated
    public static ExcelWriter getWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        return write().file(outputStream).excelType(typeEnum).needHead(needHead).autoCloseStream(Boolean.FALSE)
            .convertAllFiled(Boolean.FALSE).build();
    }

    /**
     * Get ExcelWriter with a template file
     *
     * @param temp
     *            Append value after a POI file , Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @param needHead
     *            Whether a write header is required
     * @return new ExcelWriter
     * @deprecated please use {@link EasyExcel#write()}
     */
    @Deprecated
    public static ExcelWriter getWriterWithTemp(InputStream temp, OutputStream outputStream, ExcelTypeEnum typeEnum,
        boolean needHead) {
        return write().withTemplate(temp).file(outputStream).excelType(typeEnum).needHead(needHead)
            .autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).build();
    }

    /**
     * Get ExcelWriter with a template file
     *
     * @param temp
     *            Append value after a POI file , Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @param needHead
     *            Whether a write header is required
     * @param handler
     *            User-defined callback
     * @return new ExcelWriter
     * @deprecated please use {@link EasyExcel#write()}
     */
    @Deprecated
    public static ExcelWriter getWriterWithTempAndHandler(InputStream temp, OutputStream outputStream,
        ExcelTypeEnum typeEnum, boolean needHead, WriteHandler handler) {
        return write().withTemplate(temp).file(outputStream).excelType(typeEnum).needHead(needHead)
            .registerWriteHandler(handler).autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).build();
    }

    /**
     * Build excel the write
     *
     * @return
     */
    public static ExcelWriterBuilder write() {
        return new ExcelWriterBuilder();
    }

    /**
     * Build excel the write
     *
     * @param file
     *            File to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(File file) {
        return write(file, null);
    }

    /**
     * Build excel the write
     *
     * @param file
     *            File to write
     * @param head
     *            Annotate the class for configuration information
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(File file, Class head) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(file);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param pathName
     *            File path to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(String pathName) {
        return write(pathName, null);
    }

    /**
     * Build excel the write
     *
     * @param pathName
     *            File path to write
     * @param head
     *            Annotate the class for configuration information
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(String pathName, Class head) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(pathName);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param outputStream
     *            Output stream to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(OutputStream outputStream) {
        return write(outputStream, null);
    }

    /**
     * Build excel the write
     *
     * @param outputStream
     *            Output stream to write
     * @param head
     *            Annotate the class for configuration information.
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(OutputStream outputStream, Class head) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the <code>writerSheet</code>
     *
     * @return Excel sheet writer builder
     */
    public static ExcelWriterSheetBuilder writerSheet() {
        return writerSheet(null, null);
    }

    /**
     * Build excel the <code>writerSheet</code>
     *
     * @param sheetNo
     *            Index of sheet,0 base.
     * @return Excel sheet writer builder.
     */
    public static ExcelWriterSheetBuilder writerSheet(Integer sheetNo) {
        return writerSheet(sheetNo, null);
    }

    /**
     * Build excel the 'writerSheet'
     *
     * @param sheetName
     *            The name of sheet.
     * @return Excel sheet writer builder.
     */
    public static ExcelWriterSheetBuilder writerSheet(String sheetName) {
        return writerSheet(null, sheetName);
    }

    /**
     * Build excel the 'writerSheet'
     *
     * @param sheetNo
     *            Index of sheet,0 base.
     * @param sheetName
     *            The name of sheet.
     * @return Excel sheet writer builder.
     */
    public static ExcelWriterSheetBuilder writerSheet(Integer sheetNo, String sheetName) {
        ExcelWriterSheetBuilder excelWriterSheetBuilder = new ExcelWriterSheetBuilder();
        if (sheetNo != null) {
            excelWriterSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            excelWriterSheetBuilder.sheetName(sheetName);
        }
        return excelWriterSheetBuilder;
    }

    /**
     * Build excel the <code>writerTable</code>
     *
     * @return Excel table writer builder.
     */
    public static ExcelWriterTableBuilder writerTable() {
        return writerTable(null);
    }

    /**
     * Build excel the 'writerTable'
     *
     * @param tableNo
     *            Index of table,0 base.
     * @return Excel table writer builder.
     */
    public static ExcelWriterTableBuilder writerTable(Integer tableNo) {
        ExcelWriterTableBuilder excelWriterTableBuilder = new ExcelWriterTableBuilder();
        if (tableNo != null) {
            excelWriterTableBuilder.tableNo(tableNo);
        }
        return excelWriterTableBuilder;
    }

    /**
     * Build excel the read
     *
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read() {
        return new ExcelReaderBuilder();
    }

    /**
     * Build excel the read
     *
     * @param file
     *            File to read.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(File file) {
        return read(file, null, null);
    }

    /**
     * Build excel the read
     *
     * @param file
     *            File to read.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(File file, ReadListener readListener) {
        return read(file, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param file
     *            File to read.
     * @param head
     *            Annotate the class for configuration information.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(File file, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(file);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param pathName
     *            File path to read.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(String pathName) {
        return read(pathName, null, null);
    }

    /**
     * Build excel the read
     *
     * @param pathName
     *            File path to read.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(String pathName, ReadListener readListener) {
        return read(pathName, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param pathName
     *            File path to read.
     * @param head
     *            Annotate the class for configuration information.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(String pathName, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(pathName);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param inputStream
     *            Input stream to read.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(InputStream inputStream) {
        return read(inputStream, null, null);
    }

    /**
     * Build excel the read
     *
     * @param inputStream
     *            Input stream to read.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(InputStream inputStream, ReadListener readListener) {
        return read(inputStream, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param inputStream
     *            Input stream to read.
     * @param head
     *            Annotate the class for configuration information.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(InputStream inputStream, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(inputStream);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the 'readSheet'
     *
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet() {
        return readSheet(null, null);
    }

    /**
     * Build excel the 'readSheet'
     *
     * @param sheetNo
     *            Index of sheet,0 base.
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet(Integer sheetNo) {
        return readSheet(sheetNo, null);
    }

    /**
     * Build excel the 'readSheet'
     *
     * @param sheetName
     *            The name of sheet.
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet(String sheetName) {
        return readSheet(null, sheetName);
    }

    /**
     * Build excel the 'readSheet'
     *
     * @param sheetNo
     *            Index of sheet,0 base.
     * @param sheetName
     *            The name of sheet.
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet(Integer sheetNo, String sheetName) {
        ExcelReaderSheetBuilder excelReaderSheetBuilder = new ExcelReaderSheetBuilder();
        if (sheetNo != null) {
            excelReaderSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            excelReaderSheetBuilder.sheetName(sheetName);
        }
        return excelReaderSheetBuilder;
    }

    /**
     * Read image data from an excel file
     *
     * @param pathName
     *            The path of the excel file.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    public static void readImage(String pathName, ImageDataReadListener imageDataReadListener) {
        if (pathName == null || imageDataReadListener == null) {
            throw new ExcelAnalysisException("Wrong parameter(s) in readImage().");
        }

        try {
            OPCPackage pkg = OPCPackage.open(pathName, PackageAccess.READ);
            readImage(pkg, imageDataReadListener);
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        }

    }

    /**
     * Read image data from one sheet of an excel file
     *
     * @param pathName
     *            The path of the excel file.
     * @param sheetNo
     *            The index of the sheet which you want to read data from, 1-based.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    public static void readImage(String pathName, Integer sheetNo, ImageDataReadListener imageDataReadListener) {
        if (pathName == null || sheetNo == null || imageDataReadListener == null) {
            throw new ExcelAnalysisException("Wrong parameter(s) in readImage().");
        }

        try {
            OPCPackage pkg = OPCPackage.open(pathName);
            PackagePart drawingPkg = getImagePkgPart(sheetNo, null, pkg, imageDataReadListener);
            readImage(drawingPkg, imageDataReadListener);
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Read image data from one sheet of an excel file
     *
     * @param pathName
     *            The path of the excel file.
     * @param sheetName
     *            The name of the sheet which you want to read data from.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    public static void readImage(String pathName, String sheetName, ImageDataReadListener imageDataReadListener) {
        if (pathName == null || sheetName == null || imageDataReadListener == null) {
            throw new ExcelAnalysisException("Wrong parameter(s) in readImage().");
        }

        try {
            OPCPackage pkg = OPCPackage.open(pathName);
            PackagePart drawingPkg = getImagePkgPart(null, sheetName, pkg, imageDataReadListener);
            readImage(drawingPkg, imageDataReadListener);
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Read image data from one sheet of an excel file
     *
     * @param inputStream
     *            The input stream of the excel file.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    public static void readImage(InputStream inputStream, ImageDataReadListener imageDataReadListener) {
        if (inputStream == null || imageDataReadListener == null) {
            throw new ExcelAnalysisException("Wrong parameter(s) in readImage().");
        }

        try {
            OPCPackage pkg = OPCPackage.open(inputStream);
            readImage(pkg, imageDataReadListener);
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Read image data from one sheet of an excel file
     *
     * @param inputStream
     *            The input stream of the excel file.
     * @param sheetNo
     *            The index of the sheet which you want to read data from, 1-based.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    public static void readImage(InputStream inputStream, Integer sheetNo,
        ImageDataReadListener imageDataReadListener) {
        if (inputStream == null || sheetNo == null || imageDataReadListener == null) {
            throw new ExcelAnalysisException("Wrong parameter(s) in readImage().");
        }

        try {
            OPCPackage pkg = OPCPackage.open(inputStream);
            readImage(sheetNo, null, pkg, imageDataReadListener);
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Read image data from one sheet of an excel file
     *
     * @param inputStream
     *            The input stream of the excel file.
     * @param sheetName
     *            The name of the sheet which you want to read data from.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    public static void readImage(InputStream inputStream, String sheetName,
        ImageDataReadListener imageDataReadListener) {
        if (inputStream == null || sheetName == null || imageDataReadListener == null) {
            throw new ExcelAnalysisException("Wrong parameter(s) in readImage().");
        }

        try {
            OPCPackage pkg = OPCPackage.open(inputStream);
            readImage(null, sheetName, pkg, imageDataReadListener);
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Read image data from one sheet of an OPCPackage
     *
     * @param sheetNo
     *            The index of the sheet which you want to read data from, 1-based, can be null.
     * @param sheetName
     *            The name of the sheet which you want to read data from, can be null.
     * @param pkg
     *            The OPCPackage which represents an excel file.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    private static void readImage(Integer sheetNo, String sheetName, OPCPackage pkg,
        ImageDataReadListener imageDataReadListener) {
        PackagePart drawingPkg = getImagePkgPart(sheetNo, sheetName, pkg, imageDataReadListener);
        readImage(drawingPkg, imageDataReadListener);
    }

    /**
     * Read image data from all sheets of an OPCPackage
     *
     * @param pkg
     *            The OPCPackage which represents an excel file.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    private static void readImage(OPCPackage pkg, ImageDataReadListener imageDataReadListener) {
        WorkBookXMLHandler workBookXMLHandler = new WorkBookXMLHandler();
        handleWorkBookXml(pkg, workBookXMLHandler);
        Map<String, String> sheetNameMap = workBookXMLHandler.getSheetNameMap();
        Map<Integer, String> sheetNoMap = workBookXMLHandler.getSheetNoMap();
        try {
            for (Integer sheetNo : sheetNoMap.keySet()) {
                String rId = sheetNoMap.get(sheetNo);
                PackagePart workBookPkg = workBookXMLHandler.getWorkBookPkg();
                PackagePart workSheetPkg = workBookPkg.getRelatedPart(workBookPkg.getRelationship(rId));
                PackageRelationshipCollection packageRelationshipCollection =
                    workSheetPkg.getRelationshipsByType(XSSFRelation.DRAWINGS.getRelation());
                if (packageRelationshipCollection.size() == 0) {
                    continue;
                }
                List<PackagePart> drawingPkgList = new ArrayList<PackagePart>();
                PackagePart tempDrawingPkg;
                for (PackageRelationship packageRelationship : packageRelationshipCollection) {
                    tempDrawingPkg = workSheetPkg.getRelatedPart(packageRelationship);
                    if (tempDrawingPkg.getContentType().equals(XSSFRelation.DRAWINGS.getContentType())) {
                        drawingPkgList.add(tempDrawingPkg);
                    }
                }
                PackagePart drawingPkg = drawingPkgList.get(0);
                String sheetName = null;
                for (String it : sheetNameMap.keySet()) {
                    String tempRId = sheetNameMap.get(it);
                    if (tempRId.equals(rId)) {
                        sheetName = it;
                        break;
                    }
                }

                imageDataReadListener.setRelatedSheetNo(sheetNo);
                imageDataReadListener.setRelatedSheetName(sheetName);
                readImage(drawingPkg, imageDataReadListener);
            }
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        }

    }

    /**
     * Read image data from PackagePart
     *
     * @param packagePart
     *            The PackagePart which keeps image data.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     */
    private static void readImage(PackagePart packagePart, ImageDataReadListener imageDataReadListener) {
        if (packagePart == null) {
            return;
        }

        try {
            InputStream in = packagePart.getInputStream();
            InputSource inputSource = new InputSource(packagePart.getInputStream());
            SAXParserFactory saxFactory;
            saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new DrawingXMLHandler(packagePart, imageDataReadListener));
            xmlReader.parse(inputSource);
            in.close();
        } catch (SAXException e) {
            throw new ExcelAnalysisException(e);
        } catch (ParserConfigurationException e) {
            throw new ExcelAnalysisException(e);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Get a PackagePart from OPCPackage by sheetNo or sheetName.
     *
     * @param sheetNo
     *            The index of the sheet which you want to read data from, 1-based, can be null.
     * @param sheetName
     *            The name of the sheet which you want to read data from, can be null.
     * @param pkg
     *            The OPCPackage which represents an excel file.
     * @param imageDataReadListener
     *            A listener to receive and process image data.
     * @return PackagePart
     */
    private static PackagePart getImagePkgPart(Integer sheetNo, String sheetName, OPCPackage pkg,
        ImageDataReadListener imageDataReadListener) {
        WorkBookXMLHandler workBookXMLHandler = new WorkBookXMLHandler();
        handleWorkBookXml(pkg, workBookXMLHandler);
        Map<Integer, String> sheetNoMap = workBookXMLHandler.getSheetNoMap();
        Map<String, String> sheetNameMap = workBookXMLHandler.getSheetNameMap();
        String rId = null;
        if (sheetNo != null) {
            rId = sheetNoMap.get(sheetNo);
            if (rId == null) {
                throw new ExcelAnalysisException("The sheetNo parameter is not correct!");
            }
            for (String it : sheetNameMap.keySet()) {
                String tempRId = sheetNameMap.get(it);
                if (tempRId.equals(rId)) {
                    sheetName = it;
                    break;
                }
            }
        } else if (sheetName != null) {
            rId = sheetNameMap.get(sheetName);
            if (rId == null) {
                throw new ExcelAnalysisException("The sheetName parameter is not correct!");
            }
            for (Integer it : sheetNoMap.keySet()) {
                String tempRId = sheetNoMap.get(it);
                if (tempRId.equals(rId)) {
                    sheetNo = it;
                    break;
                }
            }
        }

        imageDataReadListener.setRelatedSheetName(sheetName);
        imageDataReadListener.setRelatedSheetNo(sheetNo);

        PackagePart workBookPkg = workBookXMLHandler.getWorkBookPkg();
        try {
            PackagePart workSheetPkg = workBookPkg.getRelatedPart(workBookPkg.getRelationship(rId));

            PackageRelationshipCollection packageRelationshipCollection =
                workSheetPkg.getRelationshipsByType(XSSFRelation.DRAWINGS.getRelation());
            if (packageRelationshipCollection.size() == 0) {
                return null;
            }
            PackagePart drawingPkg = workSheetPkg.getRelatedPart(packageRelationshipCollection.getRelationship(0));
            return drawingPkg;
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    /**
     * Handle workbook XML file to get the relationships between workbook and it's worksheets.
     *
     * @param pkg
     *            The OPCPackage which represents an excel file.
     * @param workBookXMLHandler
     *            A handler to process the XML file and save result.
     */
    private static void handleWorkBookXml(OPCPackage pkg, WorkBookXMLHandler workBookXMLHandler) {
        try {
            ArrayList<PackagePart> workBookPkgList = pkg.getPartsByContentType(XSSFRelation.WORKBOOK.getContentType());
            PackagePart workBookPkg = workBookPkgList.get(0);
            workBookXMLHandler.setWorkBookPkg(workBookPkg);

            InputStream in = workBookPkg.getInputStream();
            InputSource inputSource = new InputSource(in);
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            xmlReader.setContentHandler(workBookXMLHandler);
            xmlReader.parse(inputSource);
            in.close();
        } catch (SAXException e) {
            throw new ExcelAnalysisException(e);
        } catch (ParserConfigurationException e) {
            throw new ExcelAnalysisException(e);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

}
