package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.analysis.v03.XlsRecordHandler;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Sheet;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class BOFRecordHandler extends AbstractXlsRecordHandler {
    private List<BoundSheetRecord> boundSheetRecords = new ArrayList<BoundSheetRecord>();
    private BoundSheetRecord[] orderedBSRs;
    private int sheetIndex;
    private List<Sheet> sheets;
    private AnalysisContext context;
    private boolean analyAllSheet;
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    public BOFRecordHandler(EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener, AnalysisContext context, List<Sheet> sheets) {
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
                if (orderedBSRs == null) {
                    orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                }
                sheetIndex++;

                Sheet sheet = new Sheet(sheetIndex, 0);
                sheet.setSheetName(orderedBSRs[sheetIndex - 1].getSheetname());
                sheets.add(sheet);
                if (this.analyAllSheet) {
                    context.setCurrentSheet(sheet);
                }
            }
        }
    }

    @Override
    public void init() {
        if (context.getCurrentSheet() == null) {
            this.analyAllSheet = true;
        }
        sheetIndex = 0;
        orderedBSRs = null;
        boundSheetRecords.clear();
        sheets.clear();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
