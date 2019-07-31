package com.alibaba.excel.read.listener.event;

import java.util.List;

import com.alibaba.excel.metadata.CellData;

/**
 * @author jipengfei
 */
public class EachRowAnalysisFinishEvent implements AnalysisFinishEvent {
    private List<CellData> result;

    public EachRowAnalysisFinishEvent(List<CellData> content) {
        this.result = content;
    }
    @Override
    public List<CellData> getAnalysisResult() {
        return result;
    }
}
