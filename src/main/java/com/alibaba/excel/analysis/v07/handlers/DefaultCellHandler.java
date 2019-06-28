package com.alibaba.excel.analysis.v07.handlers;

import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_VALUE_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_TAG;
import static com.alibaba.excel.constant.ExcelXmlConstants.CELL_VALUE_TAG_1;

import java.util.Arrays;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;

import com.alibaba.excel.analysis.v07.XlsxCellHandler;
import com.alibaba.excel.analysis.v07.XlsxRowResultHolder;
import com.alibaba.excel.annotation.FieldType;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventRegistryCenter;
import com.alibaba.excel.util.PositionUtils;

public class DefaultCellHandler implements XlsxCellHandler, XlsxRowResultHolder {
    private String currentCellIndex;

    private FieldType currentCellType;

    private int curRow;

    private int curCol;

    private String[] curRowContent = new String[20];

    private String currentCellValue;

    private final AnalysisContext analysisContext;
    
    private final AnalysisEventRegistryCenter registerCenter;

    private final SharedStringsTable sst;

    public DefaultCellHandler(AnalysisContext analysisContext, AnalysisEventRegistryCenter registerCenter, SharedStringsTable sst) {
        this.analysisContext = analysisContext;
        this.registerCenter = registerCenter;
        this.sst = sst;
    }
    
    @Override
    public void clearResult() {
        curRowContent = new String[20];
    }

    @Override
    public boolean support(String name) {
        return CELL_VALUE_TAG.equals(name) || CELL_VALUE_TAG_1.equals(name) || CELL_TAG.equals(name);
    }

    @Override
    public void startHandle(String name, Attributes attributes) {
        if (CELL_TAG.equals(name)) {
            currentCellIndex = attributes.getValue(ExcelXmlConstants.POSITION);
            int nextRow = PositionUtils.getRow(currentCellIndex);
            if (nextRow > curRow) {
                curRow = nextRow;
                // endRow(ROW_TAG);
            }
            analysisContext.setCurrentRowNum(curRow);
            curCol = PositionUtils.getCol(currentCellIndex);

            String cellType = attributes.getValue("t");
            currentCellType = FieldType.EMPTY;
            if (cellType != null && cellType.equals("s")) {
                currentCellType = FieldType.STRING;
            }
        }
        if (name.equals(CELL_VALUE_TAG) || name.equals(CELL_VALUE_TAG_1)) {
            // initialize current cell value
            currentCellValue = "";
        }
    }

    @Override
    public void endHandle(String name) {
        ensureSize();
        if (CELL_VALUE_TAG.equals(name)) {
            switch (currentCellType) {
                case STRING:
                    int idx = Integer.parseInt(currentCellValue);
                    currentCellValue = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                    currentCellType = FieldType.EMPTY;
                    break;
            }
            curRowContent[curCol] = currentCellValue;
        } else if (CELL_VALUE_TAG_1.equals(name)) {
            curRowContent[curCol] = currentCellValue;
        }
    }
    
    

    private void ensureSize() {
        // try to size
        if (curCol >= curRowContent.length) {
            curRowContent = Arrays.copyOf(curRowContent, (int) (curCol * 1.5));
        }
    }

    @Override
    public void appendCurrentCellValue(String currentCellValue) {
        this.currentCellValue += currentCellValue;
    }

    @Override
    public String[] getCurRowContent() {
        return this.curRowContent;
    }

    @Override
    public int getColumnSize() {
        return this.curCol;
    }

}
