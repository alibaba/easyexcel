package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FormulaRecordHandler extends AbstractXlsRecordHandler {
    private int nextRow;
    private int nextColumn;
    /**
     * Should we output the formula, or the value it has?
     */
    private boolean outputFormulaValues = true;
    private boolean outputNextStringRecord;
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

            if (outputFormulaValues) {
                if (Double.isNaN(frec.getValue())) {
                    // Formula result is a string
                    // This is stored in the next record
                    outputNextStringRecord = true;
                    nextRow = frec.getRow();
                    nextColumn = frec.getColumn();
                } else {
                    this.value = formatListener.formatNumberDateCell(frec);
                }
            } else {
                this.value = HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression());
            }
        } else if (record.getSid() == StringRecord.sid) {
            if (outputNextStringRecord) {
                // String for formula
                StringRecord srec = (StringRecord)record;
                this.value = srec.getString();
                this.row = nextRow;
                this.column = nextColumn;
                outputNextStringRecord = false;
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
