package com.alibaba.excel.read.metadata.holder.xlsx;

import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.MapUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
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
    /**
     * cache data format
     */
    private Map<Integer, DataFormatData> dataFormatDataCache;

    /**
     * excel Relationship, key: sheetNo value: PackageRelationshipCollection
     */
    private Map<Integer, PackageRelationshipCollection> packageRelationshipCollectionMap;

    public XlsxReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook);
        this.saxParserFactoryName = readWorkbook.getXlsxSAXParserFactoryName();
        setExcelType(ExcelTypeEnum.XLSX);
        dataFormatDataCache = MapUtils.newHashMap();
    }

    public DataFormatData dataFormatData(int dateFormatIndexInteger) {
        return dataFormatDataCache.computeIfAbsent(dateFormatIndexInteger, key -> {
            DataFormatData dataFormatData = new DataFormatData();
            if (stylesTable == null) {
                return null;
            }
            XSSFCellStyle xssfCellStyle = stylesTable.getStyleAt(dateFormatIndexInteger);
            if (xssfCellStyle == null) {
                return null;
            }
            dataFormatData.setIndex(xssfCellStyle.getDataFormat());
            dataFormatData.setFormat(BuiltinFormats.getBuiltinFormat(dataFormatData.getIndex(),
                xssfCellStyle.getDataFormatString(), globalConfiguration().getLocale()));
            return dataFormatData;
        });
    }

}
