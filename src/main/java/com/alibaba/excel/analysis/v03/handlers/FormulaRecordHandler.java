package com.alibaba.excel.analysis.v03.handlers;

import java.math.BigDecimal;

import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class FormulaRecordHandler implements IgnorableXlsRecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaRecordHandler.class);
    private static final String ERROR = "#VALUE!";

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        FormulaRecord frec = (FormulaRecord)record;
        CellData tempCellData = new CellData();
        tempCellData.setRowIndex(frec.getRow());
        tempCellData.setColumnIndex((int)frec.getColumn());
        CellType cellType = CellType.forInt(frec.getCachedResultType());
        String formulaValue = null;
        try {
            formulaValue = HSSFFormulaParser.toFormulaString(xlsReadContext.hsffWorkbook(), frec.getParsedExpression());
        } catch (Exception e) {
            LOGGER.debug("Get formula value error.", e);
        }
        tempCellData.setFormula(Boolean.TRUE);
        tempCellData.setFormulaValue(formulaValue);
        switch (cellType) {
            case STRING:
                // Formula result is a string
                // This is stored in the next record
                tempCellData.setType(CellDataTypeEnum.STRING);
                xlsReadContext.tempCellData(tempCellData);
                break;
            case NUMERIC:
                tempCellData.setType(CellDataTypeEnum.NUMBER);
                tempCellData.setNumberValue(BigDecimal.valueOf(frec.getValue()));
                xlsReadContext.cellMap().put((int)frec.getColumn(), tempCellData);
                break;
            case ERROR:
                tempCellData.setType(CellDataTypeEnum.ERROR);
                tempCellData.setStringValue(ERROR);
                xlsReadContext.cellMap().put((int)frec.getColumn(), tempCellData);
                break;
            case BOOLEAN:
                tempCellData.setType(CellDataTypeEnum.BOOLEAN);
                tempCellData.setBooleanValue(frec.getCachedBooleanValue());
                xlsReadContext.cellMap().put((int)frec.getColumn(), tempCellData);
                break;
            default:
                tempCellData.setType(CellDataTypeEnum.EMPTY);
                xlsReadContext.cellMap().put((int)frec.getColumn(), tempCellData);
                break;
        }
    }
}
