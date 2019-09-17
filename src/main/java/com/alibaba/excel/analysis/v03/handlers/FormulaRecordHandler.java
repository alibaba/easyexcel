package com.alibaba.excel.analysis.v03.handlers;

import java.math.BigDecimal;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class FormulaRecordHandler extends AbstractXlsRecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaRecordHandler.class);

    private static final String ERROR = "#VALUE!";
    private int nextRow;
    private int nextColumn;
    private boolean outputNextStringRecord;
    private CellData tempCellData;
    private FormatTrackingHSSFListener formatListener;
    private HSSFWorkbook stubWorkbook;

    public FormulaRecordHandler(HSSFWorkbook stubWorkbook, FormatTrackingHSSFListener formatListener) {
        this.stubWorkbook = stubWorkbook;
        this.formatListener = formatListener;
    }

    @Override
    public boolean support(Record record) {
        return FormulaRecord.sid == record.getSid() || StringRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        if (record.getSid() == FormulaRecord.sid) {
            FormulaRecord frec = (FormulaRecord)record;

            this.row = frec.getRow();
            this.column = frec.getColumn();
            CellType cellType = CellType.forInt(frec.getCachedResultType());
            String formulaValue = null;
            try {
                formulaValue = HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression());
            } catch (Exception e) {
                LOGGER.warn("Get formula value error.{}", e.getMessage());
            }
            switch (cellType) {
                case STRING:
                    // Formula result is a string
                    // This is stored in the next record
                    outputNextStringRecord = true;
                    nextRow = frec.getRow();
                    nextColumn = frec.getColumn();
                    tempCellData = new CellData(CellDataTypeEnum.STRING);
                    tempCellData.setFormula(Boolean.TRUE);
                    tempCellData.setFormulaValue(formulaValue);
                    break;
                case NUMERIC:
                    this.cellData = new CellData(BigDecimal.valueOf(frec.getValue()));
                    this.cellData.setFormula(Boolean.TRUE);
                    this.cellData.setFormulaValue(formulaValue);
                    break;
                case ERROR:
                    this.cellData = new CellData(CellDataTypeEnum.ERROR);
                    this.cellData.setStringValue(ERROR);
                    this.cellData.setFormula(Boolean.TRUE);
                    this.cellData.setFormulaValue(formulaValue);
                    break;
                case BOOLEAN:
                    this.cellData = new CellData(frec.getCachedBooleanValue());
                    this.cellData.setFormula(Boolean.TRUE);
                    this.cellData.setFormulaValue(formulaValue);
                    break;
                default:
                    this.cellData = new CellData(CellDataTypeEnum.EMPTY);
                    this.cellData.setFormula(Boolean.TRUE);
                    this.cellData.setFormulaValue(formulaValue);
                    break;
            }
        } else if (record.getSid() == StringRecord.sid) {
            if (outputNextStringRecord) {
                // String for formula
                StringRecord srec = (StringRecord)record;
                this.cellData = tempCellData;
                this.cellData.setStringValue(srec.getString());
                this.row = nextRow;
                this.column = nextColumn;
                outputNextStringRecord = false;
                tempCellData = null;
            }
        }
    }

    @Override
    public void init() {
        this.nextRow = 0;
        this.nextColumn = 0;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
