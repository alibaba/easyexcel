package com.alibaba.excel.analysis.v03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.alibaba.excel.analysis.ExcelExecutor;
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
public class XlsSaxAnalyser implements HSSFListener, ExcelExecutor {
    private boolean outputFormulaValues = true;
    private POIFSFileSystem poifsFileSystem;
    private int lastRowNumber;
    private int lastColumnNumber;
    private boolean notAllEmpty = false;
    /**
     * For parsing Formulas
     */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    private FormatTrackingHSSFListener formatListener;
    private Map<Integer, CellData> records;
    private List<ReadSheet> sheets = new ArrayList<ReadSheet>();
    private HSSFWorkbook stubWorkbook;
    private List<XlsRecordHandler> recordHandlers = new ArrayList<XlsRecordHandler>();
    private AnalysisContext analysisContext;

    public XlsSaxAnalyser(AnalysisContext context, POIFSFileSystem poifsFileSystem) throws IOException {
        this.analysisContext = context;
        this.records = new TreeMap<Integer, CellData>();
        this.poifsFileSystem = poifsFileSystem;
        analysisContext.readWorkbookHolder().setPoifsFileSystem(poifsFileSystem);
    }

    @Override
    public List<ReadSheet> sheetList() {
        return sheets;
    }

    @Override
    public void execute() {
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
        if (workbookBuildingListener != null && stubWorkbook == null) {
            stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
        }

        init();

        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            request.addListenerForAllRecords(workbookBuildingListener);
        }

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
        sheets = new ArrayList<ReadSheet>();
        buildXlsRecordHandlers();
    }

    @Override
    public void processRecord(Record record) {
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
                    records.put(thisColumn, cellData);
                }
                break;
            }
        }
        // If we got something to print out, do so
        if (cellData != null) {
            if (analysisContext.currentReadHolder().globalConfiguration().getAutoTrim()
                && CellDataTypeEnum.STRING == cellData.getType()) {
                cellData.setStringValue(cellData.getStringValue().trim());
            }
            if (CellDataTypeEnum.EMPTY != cellData.getType()) {
                notAllEmpty = true;
            }
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
        if (notAllEmpty) {
            analysisContext.readRowHolder(
                new ReadRowHolder(lastRowNumber, analysisContext.readSheetHolder().getGlobalConfiguration()));
            analysisContext.readSheetHolder().notifyEndOneRow(new EachRowAnalysisFinishEvent(records), analysisContext);
        }
        records.clear();
        lastColumnNumber = -1;
    }

    private void buildXlsRecordHandlers() {
        if (CollectionUtils.isEmpty(recordHandlers)) {
            recordHandlers.add(new BlankOrErrorRecordHandler());
            recordHandlers.add(new BofRecordHandler(workbookBuildingListener, analysisContext, sheets));
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
        }
    }
}
