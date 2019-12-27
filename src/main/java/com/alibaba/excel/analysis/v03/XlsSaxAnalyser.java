package com.alibaba.excel.analysis.v03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.analysis.v03.handlers.BlankOrErrorRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BlankRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BofRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.FormulaRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.IndexRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.LabelRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.MissingCellDummyRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NoteRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NumberRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.RkRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.SstRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.TextObjectRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.event.EachRowAnalysisFinishEvent;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.CollectionUtils;

/**
 * /** * A text extractor for Excel files. *
 * <p>
 * * Returns the textual content of the file, suitable for * indexing by something like Lucene, but not really *
 * intended for display to the user. *
 * </p>
 * *
 * <p>
 * * To turn an excel file into a CSV or similar, then see * the XLS2CSVmra example *
 * </p>
 * * * @see <a href=
 * "http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/hssf/eventusermodel/examples/XLS2CSVmra.java">XLS2CSVmra</a>
 *
 * @author jipengfei
 */
public class XlsSaxAnalyser implements HSSFListener, ExcelReadExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(XlsSaxAnalyser.class);
    //
    // private Boolean readAll;
    // private List<ReadSheet> readSheetList;
    // /**
    // * For parsing Formulas
    // */
    // private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    // private FormatTrackingHSSFListener formatListener;
    // private List<ReadSheet> sheets;
    // private HSSFWorkbook stubWorkbook;
    // private Map<Short, XlsRecordHandler> recordHandlerMap;
    private XlsReadContext xlsReadContext;
    private POIFSFileSystem poifsFileSystem;

    private static final Map<Short, XlsRecordHandler> XLS_RECORD_HANDLER_MAP = new HashMap<Short, XlsRecordHandler>(32);

    static {
        XLS_RECORD_HANDLER_MAP.put(BlankRecord.sid, new BlankRecordHandler());
    }

    public XlsSaxAnalyser(XlsReadContext xlsReadContext, POIFSFileSystem poifsFileSystem) {
        this.xlsReadContext = xlsReadContext;
        this.poifsFileSystem = poifsFileSystem;
        xlsReadContext.readWorkbookHolder().setPoifsFileSystem(poifsFileSystem);
    }

    @Override
    public List<ReadSheet> sheetList() {
        if (xlsReadContext.readSheetDataList() == null) {
            earlySheetDataList();

            LOGGER.warn("Getting the 'sheetList' before reading will cause the file to be read twice.");
            XlsListSheetListener xlsListSheetListener = new XlsListSheetListener(xlsReadContext, poifsFileSystem);
        }
        return xlsReadContext.readSheetDataList();
    }

    private void earlySheetDataList() {
        LOGGER.warn("Getting the 'sheetList' before reading will cause the file to be read twice.");

        sheetList = new ArrayList<ReadSheet>();
        bofRecordHandler = new BofRecordHandler(analysisContext, sheetList, false, false);
        bofRecordHandler.init();
        bofRecordHandler.init(null, true);

        XlsListSheetListener xlsListSheetListener = new XlsListSheetListener(xlsReadContext, poifsFileSystem);
    }

    @Override
    public void execute() {
        this.readAll = readAll;
        this.readSheetList = readSheetList;
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
        if (workbookBuildingListener != null && stubWorkbook == null) {
            stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
        }
        init();
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        request.addListenerForAllRecords(formatListener);
        try {
            factory.processWorkbookEvents(request, poifsFileSystem);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
        // Sometimes tables lack the end record of the last column
        if (!xlsReadContext.cellMap().isEmpty()) {
            endRow();
        }
    }

    private void init() {
        recordHandlerMap = new HashMap<Short, XlsRecordHandler>(32);

        buildXlsRecordHandlers();

    }

    @Override
    public void processRecord(Record record) {
        XlsRecordHandler handler = recordHandlerMap.get(record.getSid());
        if ((handler instanceof IgnorableXlsRecordHandler) && xlsReadContext.readWorkbookHolder().getIgnoreRecord03()) {
            // No need to read the current sheet
            return;
        }
        handler.processRecord(xlsReadContext, record);

        int thisRow = -1;
        int thisColumn = -1;
        CellData cellData = null;
        for (XlsRecordHandler handler : this.recordHandlers) {
            if (handler.support(record)) {
                handler.processRecord(record);
                thisRow = handler.getRow();
                thisColumn = handler.getColumn();
                cellData = handler.getCellData();
                if (cellData != null) {
                    cellData.checkEmpty();
                    if (CellDataTypeEnum.EMPTY != cellData.getType()) {
                        records.put(thisColumn, cellData);
                    }
                }
                break;
            }
        }
        // If we got something to print out, do so
        if (cellData != null && xlsReadContext.currentReadHolder().globalConfiguration().getAutoTrim()
            && CellDataTypeEnum.STRING == cellData.getType()) {
            cellData.setStringValue(cellData.getStringValue().trim());
        }

        processLastCellOfRow(record);
    }

    private boolean ignoreRecord(Record record) {
        return xlsReadContext.readWorkbookHolder().getIgnoreRecord03() && record.getSid() != BoundSheetRecord.sid
            && record.getSid() != BOFRecord.sid;
    }

    private void processLastCellOfRow(Record record) {
        // Handle end of row
        if (record instanceof LastCellOfRowDummyRecord) {
            System.out.println("----" + record.getSid());
            endRow();
        }
    }

    private void endRow() {
        xlsReadContext
            .readRowHolder(new ReadRowHolder(lastRowNumber, xlsReadContext.readSheetHolder().getGlobalConfiguration()));
        xlsReadContext.readSheetHolder().notifyEndOneRow(new EachRowAnalysisFinishEvent(records), xlsReadContext);
    }

    private void buildXlsRecordHandlers() {
        if (CollectionUtils.isEmpty(recordHandlers)) {
            recordHandlers.add(new BlankOrErrorRecordHandler(xlsReadContext));
            // The table has been counted and there are no duplicate statistics
            if (sheets == null) {
                sheets = new ArrayList<ReadSheet>();
                recordHandlers.add(new BofRecordHandler(xlsReadContext, sheets, false, true));
            } else {
                recordHandlers.add(new BofRecordHandler(xlsReadContext, sheets, true, true));
            }
            recordHandlers.add(new FormulaRecordHandler(xlsReadContext, stubWorkbook, formatListener));
            recordHandlers.add(new LabelRecordHandler(xlsReadContext));
            recordHandlers.add(new NoteRecordHandler(xlsReadContext));
            recordHandlers.add(new NumberRecordHandler(xlsReadContext, formatListener));
            recordHandlers.add(new RkRecordHandler(xlsReadContext));
            recordHandlers.add(new SstRecordHandler(xlsReadContext));
            recordHandlers.add(new MissingCellDummyRecordHandler(xlsReadContext));
            recordHandlers.add(new IndexRecordHandler(xlsReadContext));
            recordHandlers.add(new TextObjectRecordHandler(xlsReadContext));
            Collections.sort(recordHandlers);
        }

        for (XlsRecordHandler x : recordHandlers) {
            x.init();
            if (x instanceof BofRecordHandler) {
                BofRecordHandler bofRecordHandler = (BofRecordHandler)x;
                bofRecordHandler.init(readSheetList, readAll);
            }
        }
    }
}
