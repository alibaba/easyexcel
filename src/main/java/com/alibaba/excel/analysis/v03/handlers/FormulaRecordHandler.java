package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class FormulaRecordHandler extends AbstractXlsRecordHandler {
    private int nextRow;
    private int nextColumn;
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

            if (Double.isNaN(frec.getValue())) {
                // Formula result is a string
                // This is stored in the next record
                outputNextStringRecord = true;
                nextRow = frec.getRow();
                nextColumn = frec.getColumn();
            } else {
                this.cellData = new CellData(frec.getValue());
            }
        } else if (record.getSid() == StringRecord.sid) {
            if (outputNextStringRecord) {
                // String for formula
                StringRecord srec = (StringRecord)record;
                this.cellData = new CellData(srec.getString());
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
