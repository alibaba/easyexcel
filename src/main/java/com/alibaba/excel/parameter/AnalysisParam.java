package com.alibaba.excel.parameter;

import java.io.InputStream;

import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 方便使用废弃Excel解析时候入参，直接将ExcelTypeEnum、InputStream、customContent传入{@link com.alibaba.excel.ExcelReader}的构造器
 *
 * @author jipengfei
 */
@Deprecated
public class AnalysisParam {

    /**
     * @see ExcelTypeEnum
     */
    private ExcelTypeEnum excelTypeEnum;

    /**
     * file in
     */
    private InputStream in;

    /**
     * user defined param to listener use
     */
    private Object customContent;

    public AnalysisParam(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent) {
        this.in = in;
        this.excelTypeEnum = excelTypeEnum;
        this.customContent = customContent;
    }

    public ExcelTypeEnum getExcelTypeEnum() {
        return excelTypeEnum;
    }

    public void setExcelTypeEnum(ExcelTypeEnum excelTypeEnum) {
        this.excelTypeEnum = excelTypeEnum;
    }

    public Object getCustomContent() {
        return customContent;
    }

    public void setCustomContent(Object customContent) {
        this.customContent = customContent;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }
}
