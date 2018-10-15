package com.alibaba.excel.context;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author jipengfei
 */
public interface AnalysisContext {

    /**
     *
     */
    Object getCustom();

    /**
     *
     * @return current analysis sheet
     */
    Sheet getCurrentSheet();

    /**
     *
     * @param sheet
     */
    void setCurrentSheet(Sheet sheet);

    /**
     *
     * @return excel type
     */
    ExcelTypeEnum getExcelType();

    /**
     *
     * @return file io
     */
    InputStream getInputStream();

    /**
     *
     * @return
     */
    AnalysisEventListener getEventListener();

    /**
     *
     * @return
     */
    Integer getCurrentRowNum();

    /**
     *
     * @param row
     */
    void setCurrentRowNum(Integer row);

    /**
     *
     * @return
     */
    Integer getTotalCount();

    /**
     *
     * @param totalCount
     */
    void setTotalCount(Integer totalCount);

    /**
     *
     * @return
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     *
     * @param clazz
     * @param headOneRow
     */
    void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow);

    /**
     *
     * @return
     */
    boolean trim();

    /**
     *
     */
    void setCurrentRowAnalysisResult(Object result);


    /**
     *
     */
    Object getCurrentRowAnalysisResult();

    /**
     *
     */
    void interrupt();

    /**
     *
     * @return
     */
    boolean  use1904WindowDate();

    /**
     *
     * @param use1904WindowDate
     */
    void setUse1904WindowDate(boolean use1904WindowDate);
}
