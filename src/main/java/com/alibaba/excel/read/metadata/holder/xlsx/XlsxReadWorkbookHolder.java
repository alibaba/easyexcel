package com.alibaba.excel.read.metadata.holder.xlsx;

import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import com.alibaba.excel.analysis.v07.StyleWrapper;
import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.MapUtils;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

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

    private Map<Integer, StyleWrapper> map = MapUtils.newHashMap();

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

    public StyleWrapper styleWrapper(int dateFormatIndexInteger) {
        return map.computeIfAbsent(dateFormatIndexInteger, key -> {
            StyleWrapper s = new StyleWrapper();
            if (stylesTable == null) {
                return s;
            }
            XSSFCellStyle xssfCellStyle = stylesTable.getStyleAt(dateFormatIndexInteger);
            DataFormatData dataFormatData = new DataFormatData();
            dataFormatData.setIndex(xssfCellStyle.getDataFormat());
            dataFormatData.setFormat(BuiltinFormats.getBuiltinFormat(dataFormatData.getIndex(),
                xssfCellStyle.getDataFormatString(), globalConfiguration().getLocale()));
            return s;
        });

    }
}
