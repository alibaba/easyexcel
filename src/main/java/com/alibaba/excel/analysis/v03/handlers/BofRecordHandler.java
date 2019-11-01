package com.alibaba.excel.analysis.v03.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.SheetUtils;

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
    private Boolean readAll;
    private List<ReadSheet> readSheetList;
    private AnalysisContext context;
    private boolean alreadyInit;
    private boolean needInitSheet;

    public BofRecordHandler(AnalysisContext context, List<ReadSheet> sheets, boolean alreadyInit,
        boolean needInitSheet) {
        this.context = context;
        this.sheets = sheets;
        this.alreadyInit = alreadyInit;
        this.needInitSheet = needInitSheet;
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
                String sheetName = orderedBsrs[sheetIndex].getSheetname();
                // Find the currently read sheet
                ReadSheet readSheet = null;
                if (!alreadyInit) {
                    readSheet = new ReadSheet(sheetIndex, sheetName);
                    sheets.add(readSheet);
                }
                if (needInitSheet) {
                    if (readSheet == null) {
                        for (ReadSheet sheet : sheets) {
                            if (sheet.getSheetNo() == sheetIndex) {
                                readSheet = sheet;
                                break;
                            }
                        }
                    }
                    assert readSheet != null : "Can't find the sheet.";
                    context.readWorkbookHolder().setIgnoreRecord03(Boolean.TRUE);
                    // Copy the parameter to the current sheet
                    readSheet = SheetUtils.match(readSheet, readSheetList, readAll,
                        context.readWorkbookHolder().getGlobalConfiguration());
                    if (readSheet != null) {
                        if (readSheet.getSheetNo() != 0 && context.readSheetHolder() != null) {
                            // Prompt for the end of the previous form read
                            context.readSheetHolder().notifyAfterAllAnalysed(context);
                        }
                        context.currentSheet(readSheet);
                        context.readWorkbookHolder().setIgnoreRecord03(Boolean.FALSE);
                    }
                }
                sheetIndex++;
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

    public void init(List<ReadSheet> readSheetList, Boolean readAll) {
        this.readSheetList = readSheetList;
        this.readAll = readAll;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
