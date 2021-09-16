package com.alibaba.excel.analysis.v07.handlers;

import java.math.BigDecimal;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.util.BooleanUtils;
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

        // t="s" ,it means String
        // t="str" ,it means String,but does not need to be read in the 'sharedStrings.xml'
        // t="inlineStr" ,it means String,but does not need to be read in the 'sharedStrings.xml'
        // t="b" ,it means Boolean
        // t="e" ,it means Error
        // t="n" ,it means Number
        // t is null ,it means Empty or Number
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

    @Override
    public void endElement(XlsxReadContext xlsxReadContext, String name) {
        XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
        ReadCellData<?> tempCellData = xlsxReadSheetHolder.getTempCellData();
        StringBuilder tempData = xlsxReadSheetHolder.getTempData();
        String tempDataString = tempData.toString();
        CellDataTypeEnum oldType = tempCellData.getType();
        switch (oldType) {
            case STRING:
                // In some cases, although cell type is a string, it may be an empty tag
                if (StringUtils.isEmpty(tempCellData.getStringValue())) {
                    break;
                }
                String stringValue = xlsxReadContext.readWorkbookHolder().getReadCache()
                    .get(Integer.valueOf(tempCellData.getStringValue()));
                tempCellData.setStringValue(stringValue);
                break;
            case DIRECT_STRING:
            case ERROR:
                tempCellData.setStringValue(tempData.toString());
                tempCellData.setType(CellDataTypeEnum.STRING);
                break;
            case BOOLEAN:
                if (StringUtils.isEmpty(tempDataString)) {
                    tempCellData.setType(CellDataTypeEnum.EMPTY);
                    break;
                }
                tempCellData.setBooleanValue(BooleanUtils.valueOf(tempData.toString()));
                break;
            case NUMBER:
            case EMPTY:
                if (StringUtils.isEmpty(tempDataString)) {
                    tempCellData.setType(CellDataTypeEnum.EMPTY);
                    break;
                }
                tempCellData.setType(CellDataTypeEnum.NUMBER);
                tempCellData.setNumberValue(BigDecimal.valueOf(Double.parseDouble(tempDataString)));
                break;
            default:
                throw new IllegalStateException("Cannot set values now");
        }

        if (tempCellData.getStringValue() != null
            && xlsxReadContext.currentReadHolder().globalConfiguration().getAutoTrim()) {
            tempCellData.setStringValue(tempCellData.getStringValue().trim());
        }

        tempCellData.checkEmpty();
        xlsxReadSheetHolder.getCellMap().put(xlsxReadSheetHolder.getColumnIndex(), tempCellData);
    }
}
