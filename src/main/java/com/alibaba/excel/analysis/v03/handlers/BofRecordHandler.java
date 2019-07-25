package com.alibaba.excel.analysis.v03.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
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
    private boolean analyAllSheet;
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;

    public BofRecordHandler(EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener,
        AnalysisContext context, List<ReadSheet> sheets) {
        this.context = context;
        this.workbookBuildingListener = workbookBuildingListener;
        this.sheets = sheets;
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
                sheetIndex++;
                ReadSheet readSheet = new ReadSheet(sheetIndex, orderedBsrs[sheetIndex - 1].getSheetname());
                sheets.add(readSheet);
                if (this.analyAllSheet) {
                    context.currentSheet(null, readSheet);
                }
            }
        }
    }

    @Override
    public void init() {
        if (context.readSheetHolder() == null) {
            this.analyAllSheet = true;
        }
        sheetIndex = 0;
        orderedBsrs = null;
        boundSheetRecords.clear();
        sheets.clear();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
