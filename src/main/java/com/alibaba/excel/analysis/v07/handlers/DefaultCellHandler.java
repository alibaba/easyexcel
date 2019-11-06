package com.alibaba.excel.analysis.v07.handlers;

import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_DATA_FORMAT_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_FORMULA_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_INLINE_STRING_VALUE_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_VALUE_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_VALUE_TYPE_TAG;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;

import com.alibaba.excel.analysis.v07.XlsxCellHandler;
import com.alibaba.excel.analysis.v07.XlsxRowResultHolder;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.util.PositionUtils;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class DefaultCellHandler implements XlsxCellHandler, XlsxRowResultHolder {
    private final AnalysisContext analysisContext;
    private Deque<String> currentTagDeque = new LinkedList<String>();
    private int curCol;
    private Map<Integer, CellData> curRowContent = new LinkedHashMap<Integer, CellData>();
    private CellData currentCellData;
    private StringBuilder dataStringBuilder;
    private StringBuilder formulaStringBuilder;

    /**
     * Current style information
     */
    private StylesTable stylesTable;

    public DefaultCellHandler(AnalysisContext analysisContext, StylesTable stylesTable) {
        this.analysisContext = analysisContext;
        this.stylesTable = stylesTable;
    }

    @Override
    public void clearResult() {
        curRowContent = new LinkedHashMap<Integer, CellData>();
    }

    @Override
    public boolean support(String name) {
        return CELL_VALUE_TAG.equals(name) || CELL_FORMULA_TAG.equals(name) || CELL_INLINE_STRING_VALUE_TAG.equals(name)
            || CELL_TAG.equals(name);
    }

    @Override
    public void startHandle(String name, Attributes attributes) {
        currentTagDeque.push(name);
        // start a cell
        if (CELL_TAG.equals(name)) {
            curCol = PositionUtils.getCol(attributes.getValue(ExcelXmlConstants.POSITION));

            // t="s" ,it's means String
            // t="str" ,it's means String,but does not need to be read in the 'sharedStrings.xml'
            // t="inlineStr" ,it's means String
            // t="b" ,it's means Boolean
            // t="e" ,it's means Error
            // t="n" ,it's means Number
            // t is null ,it's means Empty or Number
            CellDataTypeEnum type = CellDataTypeEnum.buildFromCellType(attributes.getValue(CELL_VALUE_TYPE_TAG));
            currentCellData = new CellData(type);
            dataStringBuilder = new StringBuilder();

            // Put in data transformation information
            String dateFormatIndex = attributes.getValue(CELL_DATA_FORMAT_TAG);
            if (dateFormatIndex != null) {
                int dateFormatIndexInteger = Integer.parseInt(dateFormatIndex);
                XSSFCellStyle xssfCellStyle = stylesTable.getStyleAt(dateFormatIndexInteger);
                int dataFormat = xssfCellStyle.getDataFormat();
                String dataFormatString = xssfCellStyle.getDataFormatString();
                currentCellData.setDataFormat(dataFormat);
                if (dataFormatString == null) {
                    currentCellData.setDataFormatString(BuiltinFormats.getBuiltinFormat(dataFormat));
                } else {
                    currentCellData.setDataFormatString(dataFormatString);
                }
            }
        }
        // cell is formula
        if (CELL_FORMULA_TAG.equals(name)) {
            currentCellData.setFormula(Boolean.TRUE);
            formulaStringBuilder = new StringBuilder();
        }
    }

    @Override
    public void endHandle(String name) {
        currentTagDeque.pop();
        // cell is formula
        if (CELL_FORMULA_TAG.equals(name)) {
            currentCellData.setFormulaValue(formulaStringBuilder.toString());
            return;
        }
        if (CELL_VALUE_TAG.equals(name) || CELL_INLINE_STRING_VALUE_TAG.equals(name)) {
            CellDataTypeEnum oldType = currentCellData.getType();
            switch (oldType) {
                case DIRECT_STRING:
                case STRING:
                case ERROR:
                    currentCellData.setStringValue(dataStringBuilder.toString());
                    break;
                case BOOLEAN:
                    currentCellData.setBooleanValue(BooleanUtils.valueOf(dataStringBuilder.toString()));
                    break;
                case NUMBER:
                case EMPTY:
                    currentCellData.setType(CellDataTypeEnum.NUMBER);
                    currentCellData.setNumberValue(new BigDecimal(dataStringBuilder.toString()));
                    break;
                default:
                    throw new IllegalStateException("Cannot set values now");
            }

            if (CELL_VALUE_TAG.equals(name)) {
                // Have to go "sharedStrings.xml" and get it
                if (currentCellData.getType() == CellDataTypeEnum.STRING) {
                    String stringValue = analysisContext.readWorkbookHolder().getReadCache()
                        .get(Integer.valueOf(currentCellData.getStringValue()));
                    if (stringValue != null
                        && analysisContext.currentReadHolder().globalConfiguration().getAutoTrim()) {
                        stringValue = stringValue.trim();
                    }
                    currentCellData.setStringValue(stringValue);
                } else if (currentCellData.getType() == CellDataTypeEnum.DIRECT_STRING) {
                    currentCellData.setType(CellDataTypeEnum.STRING);
                }
            }
            // This is a special form of string
            if (CELL_INLINE_STRING_VALUE_TAG.equals(name)) {
                XSSFRichTextString richTextString = new XSSFRichTextString(currentCellData.getStringValue());
                String stringValue = richTextString.toString();
                if (stringValue != null && analysisContext.currentReadHolder().globalConfiguration().getAutoTrim()) {
                    stringValue = stringValue.trim();
                }
                currentCellData.setStringValue(stringValue);
            }

            currentCellData.checkEmpty();
            curRowContent.put(curCol, currentCellData);
        }
    }

    @Override
    public void appendCurrentCellValue(char[] ch, int start, int length) {
        String currentTag = currentTagDeque.peek();
        if (currentTag == null) {
            return;
        }
        if (CELL_FORMULA_TAG.equals(currentTag)) {
            formulaStringBuilder.append(ch, start, length);
            return;
        }
        if (!CELL_VALUE_TAG.equals(currentTag) && !CELL_INLINE_STRING_VALUE_TAG.equals(currentTag)) {
            return;
        }
        dataStringBuilder.append(ch, start, length);
    }

    @Override
    public Map<Integer, CellData> getCurRowContent() {
        return curRowContent;
    }

}
