package com.alibaba.excel.analysis.v03.handlers;

import java.math.BigDecimal;
import java.util.Map;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.FormulaData;
import com.alibaba.excel.metadata.data.ReadCellData;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
@Slf4j
public class FormulaRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {
    private static final String ERROR = "#VALUE!";

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        FormulaRecord frec = (FormulaRecord)record;
        Map<Integer, Cell> cellMap = xlsReadContext.xlsReadSheetHolder().getCellMap();
        ReadCellData<?> tempCellData = new ReadCellData<>();
        tempCellData.setRowIndex(frec.getRow());
        tempCellData.setColumnIndex((int)frec.getColumn());
        CellType cellType = CellType.forInt(frec.getCachedResultType());
        String formulaValue = null;
        try {
            formulaValue = HSSFFormulaParser.toFormulaString(xlsReadContext.xlsReadWorkbookHolder().getHssfWorkbook(),
                frec.getParsedExpression());
        } catch (Exception e) {
            log.debug("Get formula value error.", e);
        }
        FormulaData formulaData = new FormulaData();
        formulaData.setFormulaValue(formulaValue);
        tempCellData.setFormulaData(formulaData);
        xlsReadContext.xlsReadSheetHolder().setTempRowType(RowTypeEnum.DATA);
        switch (cellType) {
            case STRING:
                // Formula result is a string
                // This is stored in the next record
                tempCellData.setType(CellDataTypeEnum.STRING);
                xlsReadContext.xlsReadSheetHolder().setTempCellData(tempCellData);
                break;
            case NUMERIC:
                tempCellData.setType(CellDataTypeEnum.NUMBER);
                tempCellData.setNumberValue(BigDecimal.valueOf(frec.getValue()));
                int dataFormat =
                    xlsReadContext.xlsReadWorkbookHolder().getFormatTrackingHSSFListener().getFormatIndex(frec);
                DataFormatData dataFormatData = new DataFormatData();
                dataFormatData.setIndex((short)dataFormat);
                dataFormatData.setFormat(BuiltinFormats.getBuiltinFormat(dataFormatData.getIndex(),
                    xlsReadContext.xlsReadWorkbookHolder().getFormatTrackingHSSFListener().getFormatString(frec),
                    xlsReadContext.readSheetHolder().getGlobalConfiguration().getLocale()));
                tempCellData.setDataFormatData(dataFormatData);
                cellMap.put((int)frec.getColumn(), tempCellData);
                break;
            case ERROR:
                tempCellData.setType(CellDataTypeEnum.ERROR);
                tempCellData.setStringValue(ERROR);
                cellMap.put((int)frec.getColumn(), tempCellData);
                break;
            case BOOLEAN:
                tempCellData.setType(CellDataTypeEnum.BOOLEAN);
                tempCellData.setBooleanValue(frec.getCachedBooleanValue());
                cellMap.put((int)frec.getColumn(), tempCellData);
                break;
            default:
                tempCellData.setType(CellDataTypeEnum.EMPTY);
                cellMap.put((int)frec.getColumn(), tempCellData);
                break;
        }
    }
}
