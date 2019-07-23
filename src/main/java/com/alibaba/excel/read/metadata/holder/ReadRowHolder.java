package com.alibaba.excel.read.metadata.holder;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.Holder;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class ReadRowHolder implements Holder {

    /***
     * poi row
     */
    private Row row;
    /**
     * Some global variables
     */
    private GlobalConfiguration globalConfiguration;
    /**
     * The result of the previous listener
     */
    private Object currentRowAnalysisResult;
    /**
     * Data starting from the first row after the head is removed.Start form 1
     */
    private int relativeRowIndex;

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public GlobalConfiguration getGlobalConfiguration() {
        return globalConfiguration;
    }

    public void setGlobalConfiguration(GlobalConfiguration globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
    }

    public Object getCurrentRowAnalysisResult() {
        return currentRowAnalysisResult;
    }

    public void setCurrentRowAnalysisResult(Object currentRowAnalysisResult) {
        this.currentRowAnalysisResult = currentRowAnalysisResult;
    }

    public int getRelativeRowIndex() {
        return relativeRowIndex;
    }

    public void setRelativeRowIndex(int relativeRowIndex) {
        this.relativeRowIndex = relativeRowIndex;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.ROW;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public GlobalConfiguration globalConfiguration() {
        return getGlobalConfiguration();
    }
}
