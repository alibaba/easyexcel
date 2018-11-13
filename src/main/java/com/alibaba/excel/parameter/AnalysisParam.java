package com.alibaba.excel.parameter;

import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;

/**
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
     * the POI filesystem that contains the Workbook stream
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
