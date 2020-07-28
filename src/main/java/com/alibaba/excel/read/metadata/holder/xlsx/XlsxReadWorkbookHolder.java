package com.alibaba.excel.read.metadata.holder.xlsx;

import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.model.StylesTable;

import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
public class XlsxReadWorkbookHolder extends ReadWorkbookHolder {
    /**
     * Package
     */
    private OPCPackage opcPackage;
    /**
     * SAXParserFactory used when reading xlsx.
     * <p>
     * The default will automatically find.
     * <p>
     * Please pass in the name of a class ,like : "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
     *
     * @see SAXParserFactory#newInstance()
     * @see SAXParserFactory#newInstance(String, ClassLoader)
     */
    private String saxParserFactoryName;
    /**
     * Current style information
     */
    private StylesTable stylesTable;

    public XlsxReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook);
        this.saxParserFactoryName = readWorkbook.getXlsxSAXParserFactoryName();
        setExcelType(ExcelTypeEnum.XLSX);
    }

    public OPCPackage getOpcPackage() {
        return opcPackage;
    }

    public void setOpcPackage(OPCPackage opcPackage) {
        this.opcPackage = opcPackage;
    }

    public String getSaxParserFactoryName() {
        return saxParserFactoryName;
    }

    public void setSaxParserFactoryName(String saxParserFactoryName) {
        this.saxParserFactoryName = saxParserFactoryName;
    }

    public StylesTable getStylesTable() {
        return stylesTable;
    }

    public void setStylesTable(StylesTable stylesTable) {
        this.stylesTable = stylesTable;
    }
}
