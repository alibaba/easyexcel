package com.alibaba.excel.read.context;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.read.event.AnalysisEventListener;
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
     * @return current read sheet
     */
    Sheet getCurrentSheet();

    /**
     * 设置当前解析的Sheet
     *
     * @param sheet 入参
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
     * @return listener
     */
    AnalysisEventListener getEventListener();

    /**
     * 获取当前行数
     *
     * @return 当前行
     */
    Integer getCurrentRowNum();

    /**
     * 设置当前行数
     *
     * @param row 设置行号
     */
    void setCurrentRowNum(Integer row);

    /**
     * 返回当前sheet共有多少行数据，仅限07版excel
     *
     * @return 总行数
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * 设置总条数
     *
     * @param totalCount 总行数
     */
    void setTotalCount(Integer totalCount);

    /**
     * 返回表头信息
     *
     * @return 表头信息
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     * 构建 ExcelHeadProperty
     *
     * @param clazz 自定义model
     * @param headOneRow 表头内容
     */
    void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow);

    /**
     * 是否trim()
     *
     * @return 是否trim
     */
    boolean trim();

    /**
     *
     * @param result 解析结果
     */
    void setCurrentRowAnalysisResult(Object result);

    /**
     *
     * @return 当前行解析结果
     */
    Object getCurrentRowAnalysisResult();

    /**
     * 中断
     */
    void interrupt();

    /**
     *
     * @return 是否use1904WindowDate
     */
    boolean  use1904WindowDate();

    /**
     *
     * @param use1904WindowDate  是否use1904WindowDate
     */
    void setUse1904WindowDate(boolean use1904WindowDate);
}
