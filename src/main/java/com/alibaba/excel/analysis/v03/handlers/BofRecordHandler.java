package com.alibaba.excel.analysis.v03.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BofRecordHandler extends AbstractXlsRecordHandler {
    private List<BoundSheetRecord> boundSheetRecords = new ArrayList<BoundSheetRecord>();
    private BoundSheetRecord[] orderedBsrs;
    private int sheetIndex;
    private List<ReadSheet> sheets;
    private AnalysisContext context;
    private boolean alreadyInit;

    public BofRecordHandler(AnalysisContext context, List<ReadSheet> sheets, boolean alreadyInit) {
        this.context = context;
        this.sheets = sheets;
        this.alreadyInit = alreadyInit;
    }

    @Override
    public boolean support(Record record) {
        return BoundSheetRecord.sid == record.getSid() || BOFRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        if (record.getSid() == BoundSheetRecord.sid) {
            boundSheetRecords.add((BoundSheetRecord)record);
        } else if (record.getSid() == BOFRecord.sid) {
            BOFRecord br = (BOFRecord)record;
            if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                if (orderedBsrs == null) {
                    orderedBsrs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                }
                if (!alreadyInit) {
                    ReadSheet readSheet = new ReadSheet(sheetIndex, orderedBsrs[sheetIndex].getSheetname());
                    sheets.add(readSheet);
                }
                sheetIndex++;
                if (context.readSheetHolder() != null) {
                    if (sheetIndex == context.readSheetHolder().getSheetNo()) {
                        context.readWorkbookHolder().setIgnoreRecord03(Boolean.FALSE);
                    } else {
                        context.readWorkbookHolder().setIgnoreRecord03(Boolean.TRUE);
                    }
                }
            }
        }
    }

    @Override
    public void init() {
        sheetIndex = 0;
        orderedBsrs = null;
        boundSheetRecords.clear();
        if (!alreadyInit) {
            sheets.clear();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
