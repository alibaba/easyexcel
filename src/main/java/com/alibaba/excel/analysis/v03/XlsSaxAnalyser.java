package com.alibaba.excel.analysis.v03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.analysis.v03.handlers.BlankOrErrorRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BofRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.FormulaRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.LabelRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.MissingCellDummyRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NoteRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NumberRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.RkRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.SstRecordHandler;
import com.alibaba.excel.context.AnalysisContext;
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

    private POIFSFileSystem poifsFileSystem;
    private Boolean readAll;
    private List<ReadSheet> readSheetList;
    private int lastRowNumber;
    private int lastColumnNumber;
    /**
     * For parsing Formulas
     */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    private FormatTrackingHSSFListener formatListener;
    private Map<Integer, CellData> records;
    private List<ReadSheet> sheets;
    private HSSFWorkbook stubWorkbook;
    private List<XlsRecordHandler> recordHandlers = new ArrayList<XlsRecordHandler>();
    private AnalysisContext analysisContext;

    public XlsSaxAnalyser(AnalysisContext context, POIFSFileSystem poifsFileSystem) {
        this.analysisContext = context;
        this.records = new LinkedHashMap<Integer, CellData>();
        this.poifsFileSystem = poifsFileSystem;
        analysisContext.readWorkbookHolder().setPoifsFileSystem(poifsFileSystem);
    }

    @Override
    public List<ReadSheet> sheetList() {
        if (sheets == null) {
            LOGGER.warn("Getting the 'sheetList' before reading will cause the file to be read twice.");
            XlsListSheetListener xlsListSheetListener = new XlsListSheetListener(analysisContext, poifsFileSystem);
            sheets = xlsListSheetListener.getSheetList();
        }
        return sheets;
    }

    @Override
    public void execute(List<ReadSheet> readSheetList, Boolean readAll) {
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
        if (!records.isEmpty()) {
            endRow();
        }
    }

    private void init() {
        lastRowNumber = 0;
        lastColumnNumber = 0;
        records = new TreeMap<Integer, CellData>();
        buildXlsRecordHandlers();
    }

    @Override
    public void processRecord(Record record) {
        // Not data from the current sheet
        if (ignoreRecord(record)) {
            return;
        }
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
        if (cellData != null && analysisContext.currentReadHolder().globalConfiguration().getAutoTrim()
            && CellDataTypeEnum.STRING == cellData.getType()) {
            cellData.setStringValue(cellData.getStringValue().trim());
        }

        // Handle new row
        if (thisRow != -1 && thisRow != lastRowNumber) {
            lastColumnNumber = -1;
        }

        // Update column and row count
        if (thisRow > -1) {
            lastRowNumber = thisRow;
        }
        if (thisColumn > -1) {
            lastColumnNumber = thisColumn;
        }

        processLastCellOfRow(record);
    }

    private boolean ignoreRecord(Record record) {
        return analysisContext.readWorkbookHolder().getIgnoreRecord03() && record.getSid() != BoundSheetRecord.sid
            && record.getSid() != BOFRecord.sid;
    }

    private void processLastCellOfRow(Record record) {
        // Handle end of row
        if (record instanceof LastCellOfRowDummyRecord) {
            endRow();
        }
    }

    private void endRow() {
        if (lastColumnNumber == -1) {
            lastColumnNumber = 0;
        }
        analysisContext.readRowHolder(
            new ReadRowHolder(lastRowNumber, analysisContext.readSheetHolder().getGlobalConfiguration()));
        analysisContext.readSheetHolder().notifyEndOneRow(new EachRowAnalysisFinishEvent(records), analysisContext);
        records.clear();
        lastColumnNumber = -1;
    }

    private void buildXlsRecordHandlers() {
        if (CollectionUtils.isEmpty(recordHandlers)) {
            recordHandlers.add(new BlankOrErrorRecordHandler());
            // The table has been counted and there are no duplicate statistics
            if (sheets == null) {
                sheets = new ArrayList<ReadSheet>();
                recordHandlers.add(new BofRecordHandler(analysisContext, sheets, false, true));
            } else {
                recordHandlers.add(new BofRecordHandler(analysisContext, sheets, true, true));
            }
            recordHandlers.add(new FormulaRecordHandler(stubWorkbook, formatListener));
            recordHandlers.add(new LabelRecordHandler());
            recordHandlers.add(new NoteRecordHandler());
            recordHandlers.add(new NumberRecordHandler(formatListener));
            recordHandlers.add(new RkRecordHandler());
            recordHandlers.add(new SstRecordHandler());
            recordHandlers.add(new MissingCellDummyRecordHandler());
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
