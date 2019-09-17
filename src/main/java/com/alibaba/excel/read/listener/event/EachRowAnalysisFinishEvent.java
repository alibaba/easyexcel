package com.alibaba.excel.read.listener.event;

import java.util.Map;

import com.alibaba.excel.metadata.CellData;

/**
 * @author jipengfei
 */
public class EachRowAnalysisFinishEvent implements AnalysisFinishEvent {
    private Map<Integer, CellData> result;

    public EachRowAnalysisFinishEvent(Map<Integer, CellData> content) {
        this.result = content;
    }

    @Override
    public Map<Integer, CellData> getAnalysisResult() {
        return result;
    }
}
