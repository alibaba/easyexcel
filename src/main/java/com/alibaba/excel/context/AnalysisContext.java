package com.alibaba.excel.context;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 解析文件上下文
 *
 * @author jipengfei
 */
public interface AnalysisContext {

    /**
     * 返回用户自定义数据
     *
     * @return 返回用户自定义数据
     */
    Object getCustom();

    /**
     * 返回当前Sheet
     *
     * @return current analysis sheet
     */
    Sheet getCurrentSheet();

    /**
     * 设置当前解析的Sheet
     *
     * @param sheet
     */
    void setCurrentSheet(Sheet sheet);

    /**
     * 返回解析的Excel类型
     *
     * @return excel type
     */
    ExcelTypeEnum getExcelType();

    /**
     * 返回输入IO
     *
     * @return file io
     */
    InputStream getInputStream();

    /**
     * 获取当前监听者
     *
     * @return
     */
    AnalysisEventListener getEventListener();

    /**
     * 获取当前行数
     *
     * @return
     */
    Integer getCurrentRowNum();

    /**
     * 设置当前行数
     *
     * @param row
     */
    void setCurrentRowNum(Integer row);

    /**
     * 返回当前sheet共有多少行数据，仅限07版excel
     *
     * @return
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * 设置总条数
     *
     * @param totalCount
     */
    void setTotalCount(Integer totalCount);

    /**
     * 返回表头信息
     *
     * @return
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     * 构建 ExcelHeadProperty
     *
     * @param clazz
     * @param headOneRow
     */
    void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow);

    /**
     * 是否trim()
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
