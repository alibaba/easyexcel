package com.alibaba.excel.context;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jipengfei
 */
public class AnalysisContextImpl implements AnalysisContext {

    private Object custom;

    private Sheet currentSheet;

    private ExcelTypeEnum excelType;

    private InputStream inputStream;

    private AnalysisEventListener eventListener;

    private Integer currentRowNum;

    private Integer totalCount;

    private ExcelHeadProperty excelHeadProperty;

    private boolean trim;

    private boolean use1904WindowDate = false;

    public void setUse1904WindowDate(boolean use1904WindowDate) {
        this.use1904WindowDate = use1904WindowDate;
    }

    public Object getCurrentRowAnalysisResult() {
        return currentRowAnalysisResult;
    }

    public void interrupt() {
        throw new ExcelAnalysisException("interrupt error");
    }

    public boolean use1904WindowDate() {
        return use1904WindowDate;
    }

    public void setCurrentRowAnalysisResult(Object currentRowAnalysisResult) {
        this.currentRowAnalysisResult = currentRowAnalysisResult;
    }

    private Object currentRowAnalysisResult;

    public AnalysisContextImpl(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom,
                               AnalysisEventListener listener, boolean trim) {
        this.custom = custom;
        this.eventListener = listener;
        this.inputStream = inputStream;
        this.excelType = excelTypeEnum;
        this.trim = trim;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
        if (currentSheet.getClazz() != null) {
            buildExcelHeadProperty(currentSheet.getClazz(), null);
        }
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public Object getCustom() {
        return custom;
    }

    public void setCustom(Object custom) {
        this.custom = custom;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public AnalysisEventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(AnalysisEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public Integer getCurrentRowNum() {
        return this.currentRowNum;
    }

    public void setCurrentRowNum(Integer row) {
        this.currentRowNum = row;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ExcelHeadProperty getExcelHeadProperty() {
        return this.excelHeadProperty;
    }

    public void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow) {
        if (this.excelHeadProperty == null && (clazz != null || headOneRow != null)) {
            this.excelHeadProperty = new ExcelHeadProperty(clazz, new ArrayList<List<String>>());
        }
        if (this.excelHeadProperty.getHead() == null && headOneRow != null) {
            this.excelHeadProperty.appendOneRow(headOneRow);
        }
    }

    public boolean trim() {
        return this.trim;
    }
}
