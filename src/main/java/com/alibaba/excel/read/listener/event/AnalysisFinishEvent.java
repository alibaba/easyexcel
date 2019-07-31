package com.alibaba.excel.read.listener.event;

import java.util.Map;

import com.alibaba.excel.metadata.CellData;

/**
 *
 * Event
 *
 * @author jipengfei
 */
public interface AnalysisFinishEvent {
    /**
     * Get result
     *
     * @return
     */
    Map<Integer, CellData> getAnalysisResult();
}
