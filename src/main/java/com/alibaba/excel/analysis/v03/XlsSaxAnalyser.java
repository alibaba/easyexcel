package com.alibaba.excel.analysis.v03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.alibaba.excel.analysis.v03.handlers.BOFRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BlankOrErrorRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.FormulaRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.LabelRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.MissingCellDummyRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NoteRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NumberRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.RKRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.SSTRecordHandler;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.event.EachRowAnalysisFinishEvent;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.write.metadata.Sheet;
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
    private POIFSFileSystem fs;
    private int lastRowNumber;
    private int lastColumnNumber;
    /**
     * For parsing Formulas
     */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    private FormatTrackingHSSFListener formatListener;
    private List<String> records;
    private boolean notAllEmpty = false;
    private List<Sheet> sheets = new ArrayList<Sheet>();
    private HSSFWorkbook stubWorkbook;
    private List<XlsRecordHandler> recordHandlers = new ArrayList<XlsRecordHandler>();

    public XlsSaxAnalyser(AnalysisContext context) throws IOException {
        this.analysisContext = context;
        this.records = new ArrayList<String>();
        context.setCurrentRowNum(0);
        this.fs = new POIFSFileSystem(analysisContext.getInputStream());

    }

    @Override
    public List<Sheet> getSheets() {
        return sheets;
    }

    @Override
    public List<Sheet> sheetList() {
        return null;
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
            factory.processWorkbookEvents(request, fs);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    private void init() {
        lastRowNumber = 0;
        lastColumnNumber = 0;
        records = new ArrayList<String>();
        notAllEmpty = false;
        sheets = new ArrayList<Sheet>();
        buildXlsRecordHandlers();
    }

    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        String thisStr = null;
        for (XlsRecordHandler handler : this.recordHandlers) {
            if (handler.support(record)) {
                handler.processRecord(record);
                thisRow = handler.getRow();
                thisColumn = handler.getColumn();
                thisStr = handler.getValue();
                break;
            }
        }

        // If we got something to print out, do so
        if (thisStr != null) {
            if (analysisContext.trim()) {
                thisStr = thisStr.trim();
            }
            if (!"".equals(thisStr)) {
                notAllEmpty = true;
            }
            records.add(thisStr);
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
            int row = ((LastCellOfRowDummyRecord)record).getRow();

            if (lastColumnNumber == -1) {
                lastColumnNumber = 0;
            }
            analysisContext.setCurrentRowNum(row);
            if (notAllEmpty) {
                notify(new EachRowAnalysisFinishEvent(new ArrayList<String>(records)));
            }
            records.clear();
            lastColumnNumber = -1;
            notAllEmpty = false;
        }
    }

    private void buildXlsRecordHandlers() {
        if (CollectionUtils.isEmpty(recordHandlers)) {
            recordHandlers.add(new BlankOrErrorRecordHandler());
            recordHandlers.add(new BOFRecordHandler(workbookBuildingListener, analysisContext, sheets));
            recordHandlers.add(new FormulaRecordHandler(stubWorkbook, formatListener));
            recordHandlers.add(new LabelRecordHandler());
            recordHandlers.add(new NoteRecordHandler());
            recordHandlers.add(new NumberRecordHandler(formatListener));
            recordHandlers.add(new RKRecordHandler());
            recordHandlers.add(new SSTRecordHandler());
            recordHandlers.add(new MissingCellDummyRecordHandler());
            Collections.sort(recordHandlers);
        }

        for (XlsRecordHandler x : recordHandlers) {
            x.init();
        }
    }
}
