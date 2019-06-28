package com.alibaba.excel.context;

import java.io.InputStream;

import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 *
 * A context is the main anchorage point of a excel reader.
 * @author jipengfei
 */
public interface AnalysisContext {

    /**
     * Custom attribute
     */
    Object getCustom();

    /**
     * get current sheet
     *
     * @return current analysis sheet
     */
    Sheet getCurrentSheet();

    /**
     * set current sheet
     * @param sheet
     */
    void setCurrentSheet(Sheet sheet);

    /**
     *
     * get excel type
     * @return excel type
     */
    ExcelTypeEnum getExcelType();

    /**
     * get in io
     * @return file io
     */
    InputStream getInputStream();

    /**
     *
     * custom listener
     * @return listener
     */
    AnalysisEventListener<Object> getEventListener();

    /**
     * get the converter registry center.
     * @return converter registry center.
     */
    ConverterRegistryCenter getConverterRegistryCenter();
    /**
     * get current row
     * @return
     */
    Integer getCurrentRowNum();

    /**
     * set  current row num
     * @param row
     */
    void setCurrentRowNum(Integer row);

    /**
     * get total row ,Data may be inaccurate
     * @return
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * get total row ,Data may be inaccurate
     *
     * @param totalCount
     */
    void setTotalCount(Integer totalCount);

    /**
     * get excel head
     * @return
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     * set the excel head property
     * @param excelHeadProperty the excel property to set.
     */
    void setExcelHeadProperty(ExcelHeadProperty excelHeadProperty);

    /**
     *
     *if need to short match the content
     * @return
     */
    boolean trim();

    /**
     * set current result
     * @param result
     */
    void setCurrentRowAnalysisResult(Object result);


    /**
     * get current result
     * @return  get current result
     */
    Object getCurrentRowAnalysisResult();

    /**
     * Interrupt execution
     */
    void interrupt();

    /**
     *  date use1904WindowDate
     * @return
     */
    boolean  use1904WindowDate();

    /**
     * date use1904WindowDate
     * @param use1904WindowDate
     */
    void setUse1904WindowDate(boolean use1904WindowDate);
}
