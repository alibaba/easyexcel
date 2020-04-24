package com.alibaba.excel.analysis.v03;

import java.io.IOException;
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
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.HyperlinkRecord;
import org.apache.poi.hssf.record.IndexRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.MergeCellsRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.record.TextObjectRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.analysis.v03.handlers.BlankRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BofRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BoolErrRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.BoundSheetRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.DummyRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.EofRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.FormulaRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.HyperlinkRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.IndexRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.LabelRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.LabelSstRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.MergeCellsRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NoteRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.NumberRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.ObjRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.RkRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.SstRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.StringRecordHandler;
import com.alibaba.excel.analysis.v03.handlers.TextObjectRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;

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
    private static final short DUMMY_RECORD_SID = -1;
    private XlsReadContext xlsReadContext;
    private static final Map<Short, XlsRecordHandler> XLS_RECORD_HANDLER_MAP = new HashMap<Short, XlsRecordHandler>(32);

    static {
        XLS_RECORD_HANDLER_MAP.put(BlankRecord.sid, new BlankRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(BOFRecord.sid, new BofRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(BoolErrRecord.sid, new BoolErrRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(BoundSheetRecord.sid, new BoundSheetRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(DUMMY_RECORD_SID, new DummyRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(EOFRecord.sid, new EofRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(FormulaRecord.sid, new FormulaRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(HyperlinkRecord.sid, new HyperlinkRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(IndexRecord.sid, new IndexRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(LabelRecord.sid, new LabelRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(LabelSSTRecord.sid, new LabelSstRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(MergeCellsRecord.sid, new MergeCellsRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(NoteRecord.sid, new NoteRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(NumberRecord.sid, new NumberRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(ObjRecord.sid, new ObjRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(RKRecord.sid, new RkRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(SSTRecord.sid, new SstRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(StringRecord.sid, new StringRecordHandler());
        XLS_RECORD_HANDLER_MAP.put(TextObjectRecord.sid, new TextObjectRecordHandler());
    }

    public XlsSaxAnalyser(XlsReadContext xlsReadContext) {
        this.xlsReadContext = xlsReadContext;
    }

    @Override
    public List<ReadSheet> sheetList() {
        try {
            if (xlsReadContext.readWorkbookHolder().getActualSheetDataList() == null) {
                new XlsListSheetListener(xlsReadContext).execute();
            }
        } catch (ExcelAnalysisStopException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Custom stop!");
            }
        }
        return xlsReadContext.readWorkbookHolder().getActualSheetDataList();
    }

    @Override
    public void execute() {
        XlsReadWorkbookHolder xlsReadWorkbookHolder = xlsReadContext.xlsReadWorkbookHolder();
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        xlsReadWorkbookHolder.setFormatTrackingHSSFListener(new FormatTrackingHSSFListener(listener));
        EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener =
            new EventWorkbookBuilder.SheetRecordCollectingListener(
                xlsReadWorkbookHolder.getFormatTrackingHSSFListener());
        xlsReadWorkbookHolder.setHssfWorkbook(workbookBuildingListener.getStubHSSFWorkbook());
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        request.addListenerForAllRecords(xlsReadWorkbookHolder.getFormatTrackingHSSFListener());
        try {
            factory.processWorkbookEvents(request, xlsReadWorkbookHolder.getPoifsFileSystem());
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

    @Override
    public void processRecord(Record record) {
        XlsRecordHandler handler = XLS_RECORD_HANDLER_MAP.get(record.getSid());
        if (handler == null) {
            return;
        }
        boolean ignoreRecord = (handler instanceof IgnorableXlsRecordHandler)
            && xlsReadContext.xlsReadSheetHolder() != null && xlsReadContext.xlsReadSheetHolder().getIgnoreRecord();
        if (ignoreRecord) {
            // No need to read the current sheet
            return;
        }
        if (!handler.support(xlsReadContext, record)) {
            return;
        }
        handler.processRecord(xlsReadContext, record);
    }

}
