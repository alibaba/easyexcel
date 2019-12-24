package com.alibaba.excel.read.metadata.holder;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
public class ReadRowHolder implements Holder {
    /**
     * Returns row index of a row in the sheet that contains this cell.Start form 0.
     */
    private Integer rowIndex;

    /**
     * The result of the previous listener
     */
    private Object currentRowAnalysisResult;
    /**
     * Some global variables
     */
    private GlobalConfiguration globalConfiguration;

    /**
     * Return row comments
     * key: col index
     * value: comments
     */
    private Map<Integer, String> rowComments;

    public Map<Integer, String> getRowComments() {
        return rowComments;
    }

    public void setRowComments(Map<Integer, String> rowComments) {
        this.rowComments = rowComments;
    }

    public void addComments(Integer index, String comments) {
        this.rowComments = this.rowComments == null ? new HashMap<Integer, String>(8) : this.rowComments;
        this.rowComments.put(index, comments);
    }

    public String getComments(Integer index) {
        if (CollectionUtils.isEmpty(rowComments)) {
            return null;
        } else {
            return rowComments.get(index);
        }
    }

    public ReadRowHolder(Integer rowIndex, GlobalConfiguration globalConfiguration) {
        this.rowIndex = rowIndex;
        this.globalConfiguration = globalConfiguration;
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

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.ROW;
    }
}
