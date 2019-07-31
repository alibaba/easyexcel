package com.alibaba.excel.read.listener.event;

import java.util.List;

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
    List<CellData> getAnalysisResult();
}
