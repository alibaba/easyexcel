package com.alibaba.excel.read.metadata.holder;

import java.util.Map;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.Holder;

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
     * Row type
     */
    private RowTypeEnum rowType;
    /**
     * Cell map
     */
    private Map<Integer, Cell> cellMap;
    /**
     * The result of the previous listener
     */
    private Object currentRowAnalysisResult;
    /**
     * Some global variables
     */
    private GlobalConfiguration globalConfiguration;

    public ReadRowHolder(Integer rowIndex, RowTypeEnum rowType, GlobalConfiguration globalConfiguration,
        Map<Integer, Cell> cellMap) {
        this.rowIndex = rowIndex;
        this.rowType = rowType;
        this.globalConfiguration = globalConfiguration;
        this.cellMap = cellMap;
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

    public RowTypeEnum getRowType() {
        return rowType;
    }

    public void setRowType(RowTypeEnum rowType) {
        this.rowType = rowType;
    }

    public Map<Integer, Cell> getCellMap() {
        return cellMap;
    }

    public void setCellMap(Map<Integer, Cell> cellMap) {
        this.cellMap = cellMap;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.ROW;
    }
}
