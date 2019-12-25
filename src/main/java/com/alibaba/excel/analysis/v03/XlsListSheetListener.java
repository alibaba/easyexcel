package com.alibaba.excel.analysis.v03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.alibaba.excel.analysis.v03.handlers.BofRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * In some cases, you need to know the number of sheets in advance and only read the file once in advance.
 *
 * @author Jiaju Zhuang
 */
public class XlsListSheetListener implements HSSFListener {
    private POIFSFileSystem poifsFileSystem;
    private List<ReadSheet> sheetList;
    private BofRecordHandler bofRecordHandler;

    public XlsListSheetListener(XlsReadContext analysisContext, POIFSFileSystem poifsFileSystem) {
        this.poifsFileSystem = poifsFileSystem;
        sheetList = new ArrayList<ReadSheet>();
        bofRecordHandler = new BofRecordHandler(analysisContext, sheetList, false, false);
        bofRecordHandler.init();
        bofRecordHandler.init(null, true);
    }

    @Override
    public void processRecord(Record record) {
        bofRecordHandler.processRecord(record);
    }

    public List<ReadSheet> getSheetList() {
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        HSSFListener formatListener = new FormatTrackingHSSFListener(listener);
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener =
            new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
        request.addListenerForAllRecords(workbookBuildingListener);

        try {
            factory.processWorkbookEvents(request, poifsFileSystem);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
        return sheetList;
    }
}
