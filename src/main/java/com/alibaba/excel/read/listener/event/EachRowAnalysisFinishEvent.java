package com.alibaba.excel.read.listener.event;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.metadata.CellData;

/**
 * @author jipengfei
 */
public class EachRowAnalysisFinishEvent implements AnalysisFinishEvent {
    private Object result;

    public EachRowAnalysisFinishEvent(Object content) {
        this.result = content;
    }

    public EachRowAnalysisFinishEvent(CellData[] content, int length) {
        if (content != null) {
            List<CellData> ls = new ArrayList<CellData>(length);
            for (int i = 0; i <= length; i++) {
                ls.add(content[i]);
            }
            result = ls;
        }
    }

    @Override
    public Object getAnalysisResult() {
        return result;
    }
}
