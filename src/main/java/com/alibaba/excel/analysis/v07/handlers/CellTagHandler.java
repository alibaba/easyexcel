package com.alibaba.excel.analysis.v07.handlers;

import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_DATA_FORMAT_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_VALUE_TYPE_TAG;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.xml.sax.Attributes;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.util.PositionUtils;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class CellTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
        xlsxReadSheetHolder.setColumnIndex(PositionUtils.getCol(attributes.getValue(ExcelXmlConstants.POSITION)));

        // t="s" ,it's means String
        // t="str" ,it's means String,but does not need to be read in the 'sharedStrings.xml'
        // t="inlineStr" ,it's means String
        // t="b" ,it's means Boolean
        // t="e" ,it's means Error
        // t="n" ,it's means Number
        // t is null ,it's means Empty or Number
        CellDataTypeEnum type = CellDataTypeEnum.buildFromCellType(attributes.getValue(CELL_VALUE_TYPE_TAG));
        xlsxReadSheetHolder.setTempCellData(new CellData(type));
        xlsxReadSheetHolder.setTempData(new StringBuilder());

        // Put in data transformation information
        String dateFormatIndex = attributes.getValue(CELL_DATA_FORMAT_TAG);
        if (dateFormatIndex != null) {
            int dateFormatIndexInteger = Integer.parseInt(dateFormatIndex);
            XSSFCellStyle xssfCellStyle =
                xlsxReadContext.xlsxReadWorkbookHolder().getStylesTable().getStyleAt(dateFormatIndexInteger);
            int dataFormat = xssfCellStyle.getDataFormat();
            xlsxReadSheetHolder.getTempCellData().setDataFormat(dataFormat);
            xlsxReadSheetHolder.getTempCellData().setDataFormatString(BuiltinFormats.getBuiltinFormat(dataFormat,
                xssfCellStyle.getDataFormatString(), xlsxReadSheetHolder.getGlobalConfiguration().getLocale()));
        }
    }

}
