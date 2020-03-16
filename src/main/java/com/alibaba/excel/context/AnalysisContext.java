package com.alibaba.excel.context;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.read.processor.AnalysisEventProcessor;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 *
 * A context is the main anchorage point of a excel reader.
 *
 * @author jipengfei
 */
public interface AnalysisContext {
    /**
     * Select the current table
     *
     * @param readSheet
     *            sheet to read
     */
    void currentSheet(ReadSheet readSheet);

    /**
     * All information about the workbook you are currently working on
     *
     * @return Current workbook holder
     */
    ReadWorkbookHolder readWorkbookHolder();

    /**
     * All information about the sheet you are currently working on
     *
     * @return Current sheet holder
     */
    ReadSheetHolder readSheetHolder();

    /**
     * Set row of currently operated cell
     *
     * @param readRowHolder
     *            Current row holder
     */
    void readRowHolder(ReadRowHolder readRowHolder);

    /**
     * Row of currently operated cell
     *
     * @return Current row holder
     */
    ReadRowHolder readRowHolder();

    /**
     * The current read operation corresponds to the <code>readSheetHolder</code> or <code>readWorkbookHolder</code>
     *
     * @return Current holder
     */
    ReadHolder currentReadHolder();

    /**
     * Custom attribute
     *
     * @return
     */
    Object getCustom();

    /**
     * Event processor
     *
     * @return
     */
    AnalysisEventProcessor analysisEventProcessor();

    /**
     * Data that the customer needs to read
     *
     * @return
     */
    List<ReadSheet> readSheetList();

    /**
     * Data that the customer needs to read
     *
     * @param readSheetList
     */
    void readSheetList(List<ReadSheet> readSheetList);

    /**
     * get current sheet
     *
     * @return current analysis sheet
     * @deprecated please use {@link #readSheetHolder()}
     */
    @Deprecated
    Sheet getCurrentSheet();

    /**
     *
     * get excel type
     *
     * @return excel type
     * @deprecated please use {@link #readWorkbookHolder()}
     */
    @Deprecated
    ExcelTypeEnum getExcelType();

    /**
     * get in io
     *
     * @return file io
     * @deprecated please use {@link #readWorkbookHolder()}
     */
    @Deprecated
    InputStream getInputStream();

    /**
     * get current row
     *
     * @return
     * @deprecated please use {@link #readRowHolder()}
     */
    @Deprecated
    Integer getCurrentRowNum();

    /**
     * get total row ,Data may be inaccurate
     *
     * @return
     * @deprecated please use {@link #readRowHolder()}
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * get current result
     *
     * @return get current result
     * @deprecated please use {@link #readRowHolder()}
     */
    @Deprecated
    Object getCurrentRowAnalysisResult();

    /**
     * Interrupt execution
     *
     * @deprecated please use {@link AnalysisEventListener#hasNext(AnalysisContext)}
     */
    @Deprecated
    void interrupt();
}
