package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.util.PositionUtils;
import com.alibaba.excel.util.StringUtils;

import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.xml.sax.Attributes;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class CellTagHandler extends AbstractXlsxTagHandler {

    private static final int DEFAULT_FORMAT_INDEX = 0;

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
        xlsxReadSheetHolder.setColumnIndex(PositionUtils.getCol(attributes.getValue(ExcelXmlConstants.ATTRIBUTE_R),
            xlsxReadSheetHolder.getColumnIndex()));

        // t="s" ,it's means String
        // t="str" ,it's means String,but does not need to be read in the 'sharedStrings.xml'
        // t="inlineStr" ,it's means String
        // t="b" ,it's means Boolean
        // t="e" ,it's means Error
        // t="n" ,it's means Number
        // t is null ,it's means Empty or Number
        CellDataTypeEnum type = CellDataTypeEnum.buildFromCellType(attributes.getValue(ExcelXmlConstants.ATTRIBUTE_T));
        xlsxReadSheetHolder.setTempCellData(new ReadCellData<>(type));
        xlsxReadSheetHolder.setTempData(new StringBuilder());

        // Put in data transformation information
        String dateFormatIndex = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_S);
        Integer dateFormatIndexInteger;
        if (StringUtils.isEmpty(dateFormatIndex)) {
            dateFormatIndexInteger = DEFAULT_FORMAT_INDEX;
        } else {
            dateFormatIndexInteger = Integer.parseInt(dateFormatIndex);
        }
        StylesTable stylesTable = xlsxReadContext.xlsxReadWorkbookHolder().getStylesTable();
        if (stylesTable == null) {
            return;
        }
        XSSFCellStyle xssfCellStyle = stylesTable.getStyleAt(dateFormatIndexInteger);
        short dataFormat = xssfCellStyle.getDataFormat();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setIndex(dataFormat);
        dataFormatData.setFormat(BuiltinFormats.getBuiltinFormat(dataFormat,
            xssfCellStyle.getDataFormatString(), xlsxReadSheetHolder.getGlobalConfiguration().getLocale()));
        xlsxReadSheetHolder.getTempCellData().setDataFormatData(dataFormatData);
    }

}
